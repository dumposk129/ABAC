package com.zicure.abacconnect.work.profile;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by DUMP129 on 11/25/2015.
 */
@DatabaseTable(tableName = "work_profile")
public class WorkProfile {

    public WorkProfile() {
    }

    @DatabaseField(generatedId = true, columnName = "id")
    public Integer id;

    @DatabaseField(columnName = "work_from")
    public String work_from;

    @DatabaseField(columnName = "work_to")
    public String work_to;

    @DatabaseField(columnName = "company_name")
    public String company_name;

    @DatabaseField(columnName = "work_position")
    public String work_position;

    @DatabaseField(columnName = "user_id") // user id
    public String user_id;
}
