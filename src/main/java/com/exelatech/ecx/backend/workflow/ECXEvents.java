package com.exelatech.ecx.backend.workflow;

import com.exelatech.ecx.backend.model.ECXEvent;

public class ECXEvents {
	public static ECXEvent unknown							= new ECXEvent("UnknownECXEvent");

	// Detect (ie. Receive)
	/** Emitted from ECX-Validation.xml (Detect blueprint): a */
	public static ECXEvent dfDetected						= new ECXEvent("DataFeedDetected");
	/** Emitted from ECX-Validation.xml (Detect blueprint): a */
	public static ECXEvent dfArchived						= new ECXEvent("DataFeedArchived");
	/** Emitted from ECX-Validation.xml (Detect blueprint): a */
	public static ECXEvent dfDetectionError					= new ECXEvent("DataFeedDetectionError");
		public static ECXEvent dfMissedSLA						= new ECXEvent("DataFeedMissedSLA");
		public static ECXEvent dfResolvedMissedSLA				= new ECXEvent("ResolvedMissedSLA");

	
	// Validate
	/** Emitted from ECX-Validation.xml (Validate blueprint): a */
	public static ECXEvent dfValidate						= new ECXEvent("ValidateDataFeed");	
	/** Emitted from ECX-Validation.xml (Validate blueprint): a */
	public static ECXEvent dfValidated						= new ECXEvent("DataFeedValidated");
		// These are currently emitted as ValidationError events...
		public static ECXEvent DuplicateFileValidation			= new ECXEvent("DuplicateFileValidation");
		public static ECXEvent DatestampValidation				= new ECXEvent("DatestampValidation");
		public static ECXEvent TransactionCountValidation		= new ECXEvent("TransactionCountValidation");
		public static ECXEvent PaymentAmountValidation			= new ECXEvent("PaymentAmountValidation");
		public static ECXEvent BillerIDValidation				= new ECXEvent("BillerIDValidation");
		public static ECXEvent RemitFileValidation				= new ECXEvent("RemitFileValidation");
	// Validate Error/Failure
	/** Emitted from DataFeedValidation Talend job */
	public static ECXEvent dfValidationError				= new ECXEvent("ValidationError");
	/** Emitted from ECX-Validation.xml (Validate blueprint): a */
	public static ECXEvent dfFailedValidation				= new ECXEvent("DataFeedFailedValidation");
	
	
	// Import - Split
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent VendorInputFileSubmitted			= new ECXEvent("VendorInputFileSubmitted");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent CaptureVendorInputFile			= new ECXEvent("CaptureVendorInputFile");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent SplitVendorInputFile				= new ECXEvent("SplitVendorInputFile");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent VendorInputLogicalFile			= new ECXEvent("VendorInputLogicalFile");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent VendorGeneratedNoDataInputFile	= new ECXEvent("VendorGeneratedNoDataInputFile");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent VendorInputFileSplit				= new ECXEvent("VendorInputFileSplit");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent ArchiveVendorInputFile			= new ECXEvent("ArchiveVendorInputFile");
	/** Emitted from SplitImport-RPPS.xml (Split blueprint): a */
	public static ECXEvent SplitError						= new ECXEvent("SplitError");

	// Import
	/** Emitted from RemitImport-XXX.xml (Import blueprint): a */
	public static ECXEvent VendorRemitFileSubmitted			= new ECXEvent("VendorRemitFileSubmitted");
	/** Emitted from RemitImport-XXX.xml (Import blueprint): a */
	public static ECXEvent CaptureVendorRemitFile			= new ECXEvent("CaptureVendorRemitFile");
	/** Emitted from RemitImport-XXX.xml (Import blueprint): a */
	public static ECXEvent ParseVendorRemitFile				= new ECXEvent("ParseVendorRemitFile");
	/** Emitted from Import Talend Job */
	public static ECXEvent ValidateVendorRemitFile			= new ECXEvent("ValidateVendorRemitFile"); // eBoxRemit only?
	/** Emitted from Import Talend Job */
	public static ECXEvent PersistVendorRemitFile			= new ECXEvent("PersistVendorRemitFile"); // eBoxRemit only?
	/** Emitted from Import Talend Job : c */
	public static ECXEvent BillerRemitDataImported			= new ECXEvent("BillerRemitDataImported");
	/** Emitted from Import Talend Job */
	public static ECXEvent ValidationWarning				= new ECXEvent("ValidationWarning");
	/** Emitted from both the Import Talend Job and the RemitImport-XXX.xml (Import blueprint): a(bp) */
	public static ECXEvent ImportError						= new ECXEvent("ImportError");	// eBoxRemit only?
	/** Emitted from RemitImport-XXX.xml (Import blueprint): a */
	public static ECXEvent VendorRemitFileProcessed			= new ECXEvent("VendorRemitFileProcessed");
	/** Emitted from RemitImport-XXX.xml (Import blueprint): a */
	public static ECXEvent ArchiveVendorRemitFile			= new ECXEvent("ArchiveVendorRemitFile");
	
	
	// Biller
	/** Emitted from Biller.xml (Biller blueprint): c */
	public static ECXEvent BillerRemitDataAvailable			= new ECXEvent("BillerRemitDataAvailable");
	/** Emitted from Biller.xml (Biller blueprint): c */
	public static ECXEvent BillerRemitDataExportRequest		= new ECXEvent("BillerRemitDataExportRequest");
	/** Emitted from Biller.xml (biller blueprint): c */
	public static ECXEvent BillerRemitDataExportRequested	= new ECXEvent("BillerRemitDataExportRequested");
	/** Emitted from Biller.xml (biller blueprint): c */
	public static ECXEvent BillerError						= new ECXEvent("BillerError");
	
	
	// Export
	/** Emitted from RemitExport-XXX.xml (Export blueprint): c */
	public static ECXEvent BillerRemitExportRequestReceived	= new ECXEvent("BillerRemitExportRequestReceived");
	/** Emitted from RemitExport-XXX.xml (Export blueprint): c */
	public static ECXEvent ExportBillerRemitData			= new ECXEvent("ExportBillerRemitData");
	/** Emitted from Export Talend job : c */
	public static ECXEvent ExtractBillerRemitData 			= new ECXEvent("ExtractBillerRemitData");
	/** Emitted from Export Talend job : c */
	public static ECXEvent BillerRemitDataExtracted 		= new ECXEvent("BillerRemitDataExtracted");
	/** Emitted from RemitExport-XXX.xml (export blueprint): c */
	public static ECXEvent BillerRemitDataExported			= new ECXEvent("BillerRemitDataExported");
	/** Emitted from RemitExport-XXX.xml (export blueprint): c */
	public static ECXEvent ExportError						= new ECXEvent("ExportError");
	/** Emitted by Remit Export Monitor : c(embedded) */
	public static ECXEvent BillerRemitDataExportComplete	= new ECXEvent("BillerRemitDataExportComplete");
	/** Emitted by Remit Export Monitor : c(embedded) */
	public static ECXEvent BillerRemitDataExportError		= new ECXEvent("BillerRemitDataExportError");
	public static ECXEvent BillerRemitDataExportWarning		= new ECXEvent("BillerRemitDataExportWarning");

	
	// Send
	/** Emitted by ECX-Send.xml (delivery blueprint) */
	public static ECXEvent ExportRemitDataSent				= new ECXEvent("ExportRemitDataSent");
	/** Emitted by ECX-Send.xml (delivery blueprint) */
	public static ECXEvent SendError						= new ECXEvent("SendError");
	/** Emitted by Remit Send Monitor : c(embedded) */
	public static ECXEvent BillerRemitDataSendComplete	= new ECXEvent("BillerRemitDataSendComplete");
	/** Emitted by Remit Send Monitor : c(embedded) */
	public static ECXEvent BillerRemitDataDeliveryError 	= new ECXEvent("BillerRemitDataDeliveryError");
	
	//Returns
	
        public static ECXEvent ReturnFileImported		= new ECXEvent("ReturnFileImported");
        //confirmation received
	    public static ECXEvent VendorConfFileSubmitted		= new ECXEvent("VendorConfFileSubmitted");
		//confirmation import
		public static ECXEvent VendorConfFileProcessed		= new ECXEvent("VendorConfFileProcessed");
		//export
		public static ECXEvent VendorReturnsExportRequest		= new ECXEvent("VendorReturnsExportRequest");
		//confirmation export
		public static ECXEvent T2ReturnConfirmation         	= new ECXEvent("T2ReturnConfirmation");

		// Stop and Pos File
		public static ECXEvent PosFileDataImport         	= new ECXEvent("PosFileDataImport");
		public static ECXEvent StopFileDataImport         	= new ECXEvent("StopFileDataImport");
		public static ECXEvent PosFileDataImported         	= new ECXEvent("PosFileDataImported");
		public static ECXEvent StopFileDataImported         = new ECXEvent("StopFileDataImported");
		public static ECXEvent Error						= new ECXEvent("Error");	// Generic Error

}
