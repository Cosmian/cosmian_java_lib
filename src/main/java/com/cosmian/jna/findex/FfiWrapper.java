package com.cosmian.jna.findex;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * This maps the Findex Secure Searchable Encryption written in Rust
 */
public interface FfiWrapper extends Library {

    int set_error(String errorMsg);

    int get_last_error(byte[] output, IntByReference outputSize);

    interface FetchEntryCallback extends Callback {
        int apply(Pointer output, IntByReference outputSize, Pointer uidsPointer, int uidsLength);
    }
    interface FetchChainCallback extends Callback {
        int apply(Pointer output, IntByReference outputSize, Pointer uidsPointer, int uidsLength);
    }
    interface UpsertEntryCallback extends Callback {
        int apply(Pointer entries, int entriesLength);
    }
    interface UpsertChainCallback extends Callback {
        int apply(Pointer chains, int chainsLength);
    }

    int h_upsert(String masterKeysJson, String dbUidsAndWordsJson, FetchEntryCallback fetchEntry,
        UpsertEntryCallback upsertEntry, UpsertChainCallback upsertChain);

    int h_search(byte[] dbUidsPtr, IntByReference dbUidsSize, String masterKeysJson, String words,
        int loopIterationLimit, FetchEntryCallback fetchEntry, FetchChainCallback fetchChain);
}
