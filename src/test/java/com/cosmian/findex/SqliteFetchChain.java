package com.cosmian.findex;

import java.io.IOException;
import java.sql.SQLException;

import com.cosmian.jna.findex.FfiWrapper.FetchChainCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class SqliteFetchChain implements FetchChainCallback {
    private Sqlite sqlite;

    public SqliteFetchChain(Sqlite sqlite) {
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
        // Deserialize vector Chain Table `uid`
        //
        String[] chainTableUids = null;
        try {
            chainTableUids = mapper.readValue(uids, String[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return 1; // map 1 to IOException
        }

        //
        // Select uid and value in EntryTable
        //
        String[] values;
        try {
            values = this.sqlite.fetchChainTableItems(chainTableUids);
        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // map 2 to SQLException
        }

        //
        // Set outputs
        //
        if (values.length > 0) {
            String valuesJson;
            try {
                valuesJson = mapper.writeValueAsString(values);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return 3; // map 3 to JsonProcessingException
            }

            byte[] valuesBytes = valuesJson.getBytes();
            output.write(0, valuesBytes, 0, valuesBytes.length);
            outputSize.setValue(valuesBytes.length);
        } else {
            outputSize.setValue(0);
        }

        return 0;
    }

}
