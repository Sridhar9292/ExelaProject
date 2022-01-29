package com.exelatech.ecx.backend.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constant values used throughout the application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {
	public enum AppCodes {
		SHOV, CUTSHEET, MAILDATE
	}

    public enum DataFilter {
        REMIT_CLIENT_CODE, PRINT_APP_CODE, EMAIL_SUBACCOUNT
    }

    private Constants() {
        // hide me
    }
    //~ Static fields/initializers =============================================
    public static final String PRINT_INDEX_ALIAS_WRITE = "ecx-print-write";
    public static final String PRINT_INDEX_ALIAS_READ = "ecx-print";
    public static final String REMIT_INDEX_ALIAS_WRITE = "ecx-remit-write";
    public static final String REMIT_INDEX_ALIAS_READ = "ecx-remit";
    public static final String CONSOLE_INDEX_ALIAS_WRITE = "ecx-console-write";
    public static final String CONSOLE_INDEX_ALIAS_READ = "ecx-console";
    public static final List<String> aliasNames = new ArrayList<>( Arrays.asList(
    	PRINT_INDEX_ALIAS_WRITE,
    	PRINT_INDEX_ALIAS_READ,
    	REMIT_INDEX_ALIAS_WRITE,
    	REMIT_INDEX_ALIAS_READ,
    	CONSOLE_INDEX_ALIAS_WRITE,
    	CONSOLE_INDEX_ALIAS_READ
    ));

    /**
     * Assets Version constant
     */
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ECX_ADMIN";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String MANAGER_ROLE = "ROLE_ECX_MANAGER";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_ECX_USER";
    public static final String SHOV_PRINT_USER_ROLE = "ROLE_SHOV_PRINT_USER";
    public static final String SHOV_PRINT_ADMIN_ROLE = "ROLE_SHOV_PRINT_ADMIN";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     * @deprecated No longer used to set themes.
     */
    public static final String CSS_THEME = "csstheme";

    public static final String ECX_USERNAME = "ecx";

    /**
     * File Processing Locations (For  Integration Tests)
     */
    public static final String BASE_ENDPOINTS_DIR = "/home2/ecx/endpoints/";
    public static final String VENDORS_ENDPOINTS_DIR = BASE_ENDPOINTS_DIR+ "vendors/";
    public static final String TRANSCENTRA_ENDPOINTS_DIR = BASE_ENDPOINTS_DIR+ "transcentra/";
    public static final String EBOX_ENDPOINTS_DIR = TRANSCENTRA_ENDPOINTS_DIR + "ebox/";
    public static final String RITS_ENDPOINTS_DIR = TRANSCENTRA_ENDPOINTS_DIR + "rits/";

    public static final String ACI_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "aci/in/";
    public static final String ACI_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "aci/out/";
    public static final String FIS_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "fis/in/";
    public static final String FIS_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "fis/out/";
    public static final String FISERV_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "fiserv/in/";
    public static final String FISERV_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "fiserv/out/";
    public static final String RPPS_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "rpps/in/";
    public static final String RPPS_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "rpps/out/";
    public static final String SHOV_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "shov/in/";
    public static final String SHOV_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "shov/out/";
    public static final String WU_ENDPOINTS_DIR_IN = VENDORS_ENDPOINTS_DIR + "wu/in/";
    public static final String WU_ENDPOINTS_DIR_OUT = VENDORS_ENDPOINTS_DIR + "wu/out/";

    public static final String T2_ENDPOINTS_DIR_IN = EBOX_ENDPOINTS_DIR + "t2/in/";
    public static final String T2_ENDPOINTS_DIR_OUT = EBOX_ENDPOINTS_DIR + "t2/out/";
    public static final String RPS_ENDPOINTS_DIR_IN = EBOX_ENDPOINTS_DIR + "rps/in/";
    public static final String RPS_ENDPOINTS_DIR_OUT = EBOX_ENDPOINTS_DIR + "rps/out/";
    public static final String RETAILONE_ENDPOINTS_DIR_IN = EBOX_ENDPOINTS_DIR + "retailone/in/";
    public static final String RETAILONE_ENDPOINTS_DIR_OUT = EBOX_ENDPOINTS_DIR + "retailone/out/";

    /** Print Dashboard Related **/
    public static final String FILENAME_MATCHING_PATTERN = "\\.(PS|ps|PDF|pdf)(\\.|$)";
}
