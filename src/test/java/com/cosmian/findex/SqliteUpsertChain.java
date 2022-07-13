package com.cosmian.findex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import com.cosmian.jna.findex.FfiWrapper.UpsertChainCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.Pointer;

public class SqliteUpsertChain implements UpsertChainCallback {

    private Sqlite sqlite;

    public SqliteUpsertChain(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    @Override
    public int apply(Pointer chains, int chainsLength) {
        //
        // Read `chains` until `chainsLength`
        //
        byte[] chainsBytes = new byte[chainsLength];
        chains.read(0, chainsBytes, 0, chainsLength);

        // For the JSON strings
        ObjectMapper mapper = new ObjectMapper();

        //
        // Deserialize vector Entry Table `uid`
        //
        HashMap<String, String> uidsAndValues = new HashMap<String, String>();
        try {
            uidsAndValues = mapper.readValue(chainsBytes, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return 1; // map 1 to IOException
        }

        //
        // Insert in database
        //
        try {
            this.sqlite.databaseUpsert(uidsAndValues, "chain_table");
        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // map 2 to SQLException
        }

        return 0;
    }
}
