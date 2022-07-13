package com.cosmian;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cosmian.findex.Sqlite;
import com.cosmian.findex.SqliteFetchChain;
import com.cosmian.findex.SqliteFetchEntry;
import com.cosmian.findex.SqliteUpsertChain;
import com.cosmian.findex.SqliteUpsertEntry;
import com.cosmian.findex.UsersDataset;
import com.cosmian.jna.findex.Ffi;
import com.cosmian.jna.findex.MasterKeys;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestFfiFindex {

    @BeforeAll
    public static void before_all() {
        TestUtils.initLogging();
    }

    public byte[] hash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passHash = sha256.digest(data);
        return passHash;
    }

    @Test
    public void testUpsertAndSearch() throws Exception {
        System.out.println("");
        System.out.println("---------------------------------------");
        System.out.println("Findex Upsert");
        System.out.println("---------------------------------------");
        System.out.println("");

        //
        // Recover master keys (k and k*)
        //
        String masterKeysJson = Resources.load_resource("findex/keys.json");
        MasterKeys masterKeys = MasterKeys.fromJson(masterKeysJson);

        //
        // Recover test vectors
        //
        ObjectMapper mapper = new ObjectMapper();
        String expectedSearchResultsString = Resources.load_resource("findex/search_results.json");
        String[] expectedSearchResults = mapper.readValue(expectedSearchResultsString, String[].class);
        Arrays.sort(expectedSearchResults);

        //
        // Build dataset with DB uids and words
        //
        String dataJson = Resources.load_resource("findex/data.json");
        UsersDataset[] testFindexDataset = UsersDataset.fromJson(dataJson);
        HashMap<String, String[]> dbUidsAndWords = new HashMap<String, String[]>();
        int count = 1;
        for (UsersDataset user : testFindexDataset) {
            String dbUid = "";
            String pattern = String.format("%02X", count).replace(' ', '0').toLowerCase();
            for (int i = 0; i < 16; i++) {
                dbUid += pattern;
            }
            dbUidsAndWords.put(dbUid, user.values());
            count++;
        }

        //
        // Prepare Sqlite tables and users
        //
        Sqlite db = new Sqlite();
        db.insertUsers(testFindexDataset);

        //
        // Declare all callbacks
        //
        SqliteFetchEntry fetchEntry = new SqliteFetchEntry(db);
        SqliteUpsertEntry upsertEntry = new SqliteUpsertEntry(db);
        SqliteUpsertChain upsertChain = new SqliteUpsertChain(db);
        SqliteFetchChain fetchChain = new SqliteFetchChain(db);

        //
        // Upsert
        //
        Ffi.upsert(masterKeys, dbUidsAndWords, fetchEntry, upsertEntry, upsertChain);
        System.out.println("After insertion: entry_table: nb indexes: " + db.getAllKeyValueItems("entry_table").size());
        System.out.println("After insertion: chain_table: nb indexes: " + db.getAllKeyValueItems("chain_table").size());

        //
        // Search
        //
        System.out.println("");
        System.out.println("---------------------------------------");
        System.out.println("Findex Search");
        System.out.println("---------------------------------------");
        System.out.println("");

        String[] dbUids = Ffi.search(masterKeys, new String[] {"France"}, 100, fetchEntry, fetchChain);

        System.out.println("DB UIDS found: " + Arrays.toString(dbUids));
        assertArrayEquals(dbUids, expectedSearchResults);
    }

}
