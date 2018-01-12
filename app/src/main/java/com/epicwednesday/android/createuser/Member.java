package com.epicwednesday.android.createuser;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mjohnson on 1/11/18.
 */

public class Member {
    private UUID mUUID;
    private Date mCreationDate;
    private ContactsContract.CommonDataKinds.Email mEmail;

    public Member(){
        setUUID(UUID.randomUUID());
        setCreationDate(new Date());

    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    public ContactsContract.CommonDataKinds.Email getEmail() {
        return mEmail;
    }

    public void setEmail(ContactsContract.CommonDataKinds.Email email) {
        mEmail = email;
    }
}
