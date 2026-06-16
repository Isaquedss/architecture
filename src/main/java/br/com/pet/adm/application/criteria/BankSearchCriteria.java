package br.com.pet.adm.application.criteria;

import java.util.Date;

/**
 * Critério de busca da camada Application.
 * Versão limpa do BankFilter — sem anotações Spring/Swagger.
 */
public class BankSearchCriteria {

    private final String  cdBank;
    private final String  dsBank;
    private final Integer activeFlag;
    private final Date    creationDate;
    private final Date    changeDate;
    private final Date    inactivationDate;
    private final Long    userId;
    private final String  orderBy;
    private final Integer page;
    private final Integer pageSize;

    private BankSearchCriteria(Builder builder) {
        this.cdBank           = builder.cdBank;
        this.dsBank           = builder.dsBank;
        this.activeFlag       = builder.activeFlag;
        this.creationDate     = builder.creationDate;
        this.changeDate       = builder.changeDate;
        this.inactivationDate = builder.inactivationDate;
        this.userId           = builder.userId;
        this.orderBy          = builder.orderBy;
        this.page             = builder.page;
        this.pageSize         = builder.pageSize;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String  cdBank;
        private String  dsBank;
        private Integer activeFlag  = 1;
        private Date    creationDate;
        private Date    changeDate;
        private Date    inactivationDate;
        private Long    userId;
        private String  orderBy   = "dsBank,asc";
        private Integer page      = 0;
        private Integer pageSize  = Integer.MAX_VALUE;

        public Builder cdBank(String v)                   { this.cdBank = v; return this; }
        public Builder dsBank(String v)                   { this.dsBank = v; return this; }
        public Builder activeFlag(Integer v)              { this.activeFlag = v; return this; }
        public Builder creationDate(Date v)               { this.creationDate = v; return this; }
        public Builder changeDate(Date v)                 { this.changeDate = v; return this; }
        public Builder inactivationDate(Date v)           { this.inactivationDate = v; return this; }
        public Builder userId(Long v)                     { this.userId = v; return this; }
        public Builder orderBy(String v)                  { this.orderBy = v; return this; }
        public Builder page(Integer v)                    { this.page = v; return this; }
        public Builder pageSize(Integer v)                { this.pageSize = v; return this; }
        public BankSearchCriteria build()                 { return new BankSearchCriteria(this); }
    }

    public String  getCdBank()           { return cdBank; }
    public String  getDsBank()           { return dsBank; }
    public Integer getActiveFlag()       { return activeFlag; }
    public Date    getCreationDate()     { return creationDate; }
    public Date    getChangeDate()       { return changeDate; }
    public Date    getInactivationDate() { return inactivationDate; }
    public Long    getUserId()           { return userId; }
    public String  getOrderBy()          { return orderBy; }
    public Integer getPage()             { return page; }
    public Integer getPageSize()         { return pageSize; }
}
