package com.cosmian.findex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import com.cosmian.jna.findex.FfiWrapper.UpsertEntryCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.Pointer;

public class SqliteUpsertEntry implements UpsertEntryCallback {

    private Sqlite sqlite;

    public SqliteUpsertEntry(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    @Override
    public int apply(Pointer entries, int entriesLength) {
        //
        // Read `entries` until `entriesLength`
        //
        byte[] entriesBytes = new byte[entriesLength];
        entries.read(0, entriesBytes, 0, entriesLength);

        // For the JSON strings
        ObjectMapper mapper = new ObjectMapper();

        //
        // Deserialize vector Entry Table `uid`
        //
        HashMap<String, String> uidsAndValues = new HashMap<String, String>();
        try {
            uidsAndValues = mapper.readValue(entriesBytes, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return 1; // map 1 to IOException
        }

        //
        // Insert in database
        //
        try {
            this.sqlite.databaseUpsert(uidsAndValues, "entry_table");
        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // map 2 to SQLException
        }

        return 0;
    }

}
