package com.cosmian.findex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import com.cosmian.jna.findex.FfiWrapper.FetchEntryCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class SqliteFetchEntry implements FetchEntryCallback {
    private Sqlite sqlite;

    public SqliteFetchEntry(Sqlite sqlite) {
        this.sqlite = sqlite;
    }

    @Override
    public int apply(Pointer output, IntByReference outputSize, Pointer uidsPointer, int uidsLength) {
        //
        // Read `uidsPointer` until `uidsLength`
        //
        byte[] uids = new byte[uidsLength];
        uidsPointer.read(0, uids, 0, uidsLength);

        // For the JSON strings
        ObjectMapper mapper = new ObjectMapper();

        //
        // Deserialize vector Entry Table `uid`
        //
        String[] entryTableUids = null;
        try {
            entryTableUids = mapper.readValue(uids, String[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return 1; // map 1 to IOException
        }

        //
        // Select uid and value in EntryTable
        //
        HashMap<String, String> uidsAndValues = new HashMap<String, String>();
        try {
            uidsAndValues = this.sqlite.fetchEntryTableItems(entryTableUids);
        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // map 2 to SQLException
        }

        //
        // Set outputs
        //
        if (uidsAndValues.size() > 0) {
            String uidsAndValuesJson;
            try {
                uidsAndValuesJson = mapper.writeValueAsString(uidsAndValues);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return 3; // map 3 to JsonProcessingException
            }

            byte[] uidsAndValuesBytes = uidsAndValuesJson.getBytes();
            output.write(0, uidsAndValuesBytes, 0, uidsAndValuesBytes.length);
            outputSize.setValue(uidsAndValuesBytes.length);
        } else {
            outputSize.setValue(0);
        }

        return 0;
    }

}
