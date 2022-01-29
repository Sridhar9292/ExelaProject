package com.exelatech.ecxapi.util;

public class Constants {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "auth";
	public static final String LANDING_PAGE = "landingPage";
	public final static String SUCCESS_STATUS = "Success";
	public final static String FAILED_STATUS = "Error";

	//Validation Error Message
	public final static String ALREDY_EXIST = " Already Exist ";
	public final static String NOT_EXIST = " Not Exist ";
	public final static String AUTH_ERROR = " Invalid username / password ";
	public final static String AUTH_INVALID_USER = " Invalid user. Please Register in ECX";
	public final static String ACCT_ENABLE_ERROR = " Account not enabled for the user ";
	public final static String LDAP_USER_ERROR = " Not an LDAP user ";

	//Exception Message
	public final static String NOT_UPDATE = " Data Not Updated ";
	public final static String NOT_INSERT = " Data Not Inserted ";
	public final static String NOT_DELETE = " Data Not deleted ";
	public final static String NO_DATA = " Data Empty ";

	public final static String LDAP_ERROR = "Ldap Connection refused ";
	public static final String ERROR_SUBJECT_TOKEN_VALIDATION_FAILED = "Token validation with Subject failed";
	public static final String ERROR_SIGNATURE_VALIDATION_FAILED = "Token Signature validation failed, token modified";

	//permissions   




	public static String  VIEW =":view";
	public static String  ADD =":add";
	public static String  EDIT =":edit";
	public static String  DELETE =":delete";
	public static String  MANAGE =":manage";
	public static String  GENERATE =":generateReport";
	public static String  SENT =":send";
	public static String  UPDATE =":update";
	public static String  COMMENT =":comment";
	public static String  EXPORT =":export";
	
	public static String  ADMIN_VIEW ="_administration:*:view";
	public static String  ADMIN_ADD ="_administration:*:add";
	public static String  ADMIN_EDIT ="_administration:*:edit";
	public static String  ADMIN_DELETE ="_administration:*:delete";
	public static String  ADMIN_MANAGE ="_administration:*:manage";
	
	public static String  PRINT_VIEW ="_print:*:view";
	public static String  PRINT_SENT ="_print:*:send";
	public static String  PRINT_UPDATE ="_print:*:update";
	public static String  PRINT_MANAGE ="_print:*:manage";

	public static String  EMAIL_VIEW ="_email:*:view";
	public static String  EMAIL_ADD ="_email:*:add";
	public static String  EMAIL_EDIT ="_email:*:edit";
	public static String  EMAIL_DELETE ="_email:*:delete";
	public static String  EMAIL_MANAGE ="_email:*:manage";

	public static String  REPORT_VIEW ="_reports:*:view";
	public static String  REPORT_MANAGE ="_reports:*:manage";
	public static String  REPORT_GENERATE ="_reports:*:generateReport";

	public static String  REMIT_VIEW ="_remit:*:view";
	public static String  REMIT_ADD ="_remit:*:add";
	public static String  REMIT_EDIT ="_remit:*:edit";
	public static String  REMIT_DELETE ="_remit:*:delete";
	public static String  REMIT_MANAGE ="_remit:*:manage";
	public static String  REMIT_COMMENT ="_remit:*:comment";
	
	
	public static String  PRINTHUB_VIEW ="_printhub:*:view";
	public static String  PRINTHUB_MANAGE ="_printhub:*:manage";
	
	


	public static String  ADMIN_VIEW_PERMISSION="_administration:user:view,_administration:role:view,_administration:permission:view,_administration:holiday:view,_administration:file:view,_administration:template:view,_administration:setting:view";
	public static String  ADMIN_ADD_PERMISSION="_administration:user:add,_administration:role:add,_administration:permission:add,_administration:holiday:add,_administration:file:add,_administration:template:add,_administration:setting:add";
	public static String  ADMIN_DELETE_PERMISSION="_administration:user:delete,_administration:role:delete,_administration:permission:delete,_administration:holiday:delete,_administration:file:delete,_administration:template:delete,_administration:setting:delete";
	public static String  ADMIN_EDIT_PERMISSION="_administration:user:edit,_administration:role:edit,_administration:permission:edit,_administration:holiday:edit,_administration:file:edit,_administration:template:edit,_administration:setting:edit";
	public static String  ADMIN_MANAGE_PERMISSION ="_administration:user:manage,_administration:role:manage,_administration:permission:manage,_administration:holiday:manage,_administration:file:manage,_administration:template:manage,_administration:setting:manage";

	public static String  PRINT_VIEW_PERMISSION ="_print:kaiserProcessSummary:view,_print:kaiserPaperSummary:view,_print:acgDashboard:view,_print:acgExceptionsDashboard:view,_print:massMutualDashboard:view,_print:kaiserFileMonitor:view,_print:kaiserFeedbackReport:view";
	public static String  PRINT_MANAGE_PERMISSION ="_print:kaiserProcessSummary:manage,_print:kaiserPaperSummary:manage,_print:acgDashboard:manage,_print:acgExceptionsDashboard:manage,_print:massMutualDashboard:manage,_print:kaiserFileMonitor:manage,_print:kaiserFeedbackReport:manage";
	public static String  PRINT_SEND_PERMISSION ="_print:kaiserPaperSummary:send";
	public static String  PRINT_UPDATE_PERMISSION ="_print:kaiserPaperSummary:update";

	public static String  PRINTHUB_VIEW_PERMISSION ="_printhub:printWorkflow:view,_printhub:printFileStatus:view,_printhub:farmingDale:view";
	public static String  PRINTHUB_MANAGE_PERMISSION ="_printhub:printWorkflow:manage,_printhub:printFileStatus:manage,_printhub:farmingDale:manage";

	public static String  REPORT_VIEW_PERMISSION ="_reports:monthlyReport:view,_reports:invalidMessageReport:view,_reports:summaryReport:view";
	public static String  REPORT_GENERATE_PERMISSION ="_reports:summaryReport:generateReport,_reports:monthlyReport:generateReport,_reports:invalidMessageReport:generateReport";
	public static String  REPORT_MANAGE_PERMISSION ="_reports:summaryReport:manage,_reports:monthlyReport:manage,_reports:invalidMessageReport:manage";

	public static String  EMAIL_VIEW_PERMISSION ="_email:emailDashboard:view,_email:subaccountDashboard:view";
	public static String  EMAIL_MANAGE_PERMISSION ="_email:emailDashboard:manage,_email:subaccountDashboard:manage";
	public static String  EMAIL_ADD_PERMISSION ="_email:subaccountDashboard:add";
	public static String  EMAIL_EDIT_PERMISSION ="_email:subaccountDashboard:edit";
	public static String  EMAIL_DELETE_PERMISSION ="_email:subaccountDashboard:delete";

	public static String  REMIT_VIEW_PERMISSION ="_remit:remitDashboard:view,_remit:returnDashboard:view,_remit:billerManagement:view";
	public static String  REMIT_MANAGE_PERMISSION ="_remit:remitDashboard:manage,_remit:returnDashboard:manage,_remit:billerManagement:manage";
	public static String  REMIT_COMMENT_PERMISSION ="_remit:remitDashboard:comment,_remit:returnDashboard:comment";
	public static String  REMIT_ADD_PERMISSION ="_remit:billerManagement:add";
	public static String  REMIT_EDIT_PERMISSION ="_remit:billerManagement:edit";
	public static String  REMIT_DELETE_PERMISSION ="_remit:billerManagement:delete";


}
