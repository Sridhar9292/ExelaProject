package com.exelatech.ecx.backend.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.exelatech.ecx.backend.domain.EcxRules;
import com.exelatech.ecx.backend.util.JsonUtl;

@NamedNativeQueries({ @NamedNativeQuery(name = "jobactivity.orders", query = " SELECT  " + "        ord.MajorWO"
        + "      , ord.VendorOrder as JobNumber" + "      , ord.CustNbr" + "      , ord.JobNbr as AppCode"
        + "      , ord.JobName" + "      , ord.StartDate" + "      , ord.DueDate" + "      , ord.Status"
        + "      , MAX(p.eventDateTime) as PrintDate" + "      , MAX(M.eventDateTime) as MailDate"
        + "  FROM dbo.DashBoard_ECX_Orders ord with(nolock)"
        + "  left outer join dbo.DashBoard_ECX_PrintEvents p with(nolock) on p.SalesOrder=ord.SalesOrder"
        + "  left outer join dbo.DashBoard_ECX_MeterEvents m with(nolock) on m.SalesOrder=ord.SalesOrder"
        + "  where ord.StartDate between :begin_date and  getdate()"
        + " and ord.VendorOrder is not null and ord.MajorWO is not null   group by " + "      ord.VendorOrder"
        + "      , ord.MajorWO" + "      , ord.CustNbr" + "      , ord.JobNbr" + "      , ord.JobName"
        + "      , ord.StartDate" + "      , ord.DueDate"
        + "      , ord.Status", resultSetMapping = "jobActivityOrders") })
@SqlResultSetMapping(name = "jobActivityOrders", classes = @ConstructorResult(targetClass = Prisma.class, columns = {
        @ColumnResult(name = "MajorWO", type = String.class), @ColumnResult(name = "JobNumber", type = String.class),
        @ColumnResult(name = "CustNbr", type = String.class), @ColumnResult(name = "AppCode", type = String.class),
        @ColumnResult(name = "JobName", type = String.class), @ColumnResult(name = "StartDate", type = Date.class),
        @ColumnResult(name = "DueDate", type = Date.class), @ColumnResult(name = "Status", type = String.class),
        @ColumnResult(name = "PrintDate", type = Date.class), @ColumnResult(name = "MailDate", type = Date.class), }))

@Entity
@Table(name = "ECX.ECX_LOOKUP_TABLE")
@NamedQueries({
        @NamedQuery(name = "EcxLookupTable.getByType", query = "SELECT c FROM EcxLookup c WHERE TRIM(c.Id.Type)= TRIM(:type)") })
public class EcxLookup {

    @Id
    private EcxLookupPK Id;

    @Column(name = "DESCRIPTION")
    private String Description;

    @Column(name = "ACTIVE")
    private char Active;

    @Column(name = "MODIFY_DATE")
    private Date ModifyDate;

    @Column(name = "ENTRY_DATE")
    private Date EntryDate;

    @Column(name = "USER_ABBR")
    private String UserABBR;

    @Column(name = "DEFAULT_VALUE")
    private char DefaultValue;

    @Transient
    private EcxRules Rule;

    public EcxLookupPK getId() {
        return Id;
    }

    public void setId(EcxLookupPK id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public char getActive() {
        return Active;
    }

    public void setActive(char active) {
        Active = active;
    }

    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }

    public Date getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(Date entryDate) {
        EntryDate = entryDate;
    }

    public String getUserABBR() {
        return UserABBR;
    }

    public void setUserABBR(String userABBR) {
        UserABBR = userABBR;
    }

    public char getDefaultValue() {
        return DefaultValue;
    }

    public void setDefaultValue(char defaultValue) {
        DefaultValue = defaultValue;
    }

    public EcxRules getRule() {
        if (this.getDescription() != null && this.Rule == null) {
            setRule(JsonUtl.JsonStrToObject(this.getDescription(), EcxRules.class));
        }
        return Rule;
    }

    public void setRule(EcxRules rule) {
        Rule = rule;
    }

    public EcxLookup() {
    }
}