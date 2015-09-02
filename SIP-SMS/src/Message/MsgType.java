package Message;

public enum MsgType {

	// SMS-SEE
	SERVICEDEPLOY, SERVICELIST, SERVICECOPYLIST, HOSTLIST, UNDEPLOYEDSERVICE, HOSTSIZE,// ������
	SERVICEDIRECTDEPLOY, SERVICEDEPLOYFLAG, SERVICESTATUS, SERVICEDELETE, SETCOPYNUM, SETCCRNUM, FILTER, FILTERSERVICE, SIZENUM,FILTERSIZE,// �������
	NODELIST, SERVICEDETAIL, STRGLIST, SELECTSTRG, SETSTRGPARA, FILTERSTRATEGY, CFILTERSTRATEGY, // ���Թ���
	NODEDELETE, NODEDETAIL, NODESTATUS, NODEEXIST,

	NODESET, NODEINSERT, FILTERNODE, CFILTERNODE,

	FLOWLIST, FLOWCOPYLISTRESULT, FLOWCOPYLIST, FLOWLIST2RESULT, FLOWLIST2, FLOWTRANS, BSNSFLOWDEPLOY, FLOWEXECUTE, // ҵ�����̽�ģ
	SERVFLOWDEPLOY, // �������̽�ģ
	FLOWSTATUS, // ������ͣ

	// SEE-SMS �������
	SERVICELISTRESULT, HOSTLISTRESULT, SERVICECOPYLISTRESULT, // ������
	SERVICEDEPLOYRESULT, SERVICESTATUSRESULT, SERVICEDELETERESULT, SETCOPYNUMRESULT, SETCCRNUMRESULT, FILTERRESULT, FILTERSERVICERESULT, // �������
	NODELISTRESULT, NODEDELETERESULT, CFILTERNODERESULT, FILTERNODERESULT,

	NODESETRESULT, NODEINSERTRESULT,

	STRGLISTRESULT, SELECTSTRGRESULT, SETSTRGPARARESULT, FILTERSTRATEGYRESULT, CFILTERSTRATEGYRESULT, // ���Թ���

	FLOWLISTRESULT, FLOWTRANSRESULT, FLOWDEPLOYRESULT, FLOWEXECUTERESULT, // ҵ�����̽�ģ
	// �������̽�ģ
	FLOWSTATUSRESULT, // ������ͣ���

	// ���˽ṹ
	serviceCopyRequest, serviceCopy, HOSTINFO, HOSTHARDDISK, singleServiceCallTimesRequest, singleServiceCallTimes, singleServiceRunTimeRequest, singleServiceRunTime,

	// SMS-SCAS
	SENDTYPE, SENDTYPERESULT, SENDFILE, DeleteService, DeleteServiceResult, FLOWEXECUTE2, StartFlow, changeServiceStatus, EndFlow, ChangeFlowStatus, changeFlowStatus,
	// SCAS-SMS
	SENDFILERESULT, RECEIVEFILERESULT, FLOWEXECUTE2RESULT, StartFlowResult, changeServiceStatusResult, EndFlowResult, ChangeFlowStatusResult, changeFlowStatusResult,

	// SMS-engine
	SENDFLOW,
	// engine-SMS
	SENDFLOWRESULT,

	SERVICEEXIST, SERVICEEXISTRESULT,
	// userManage
	USEREXISTRESULT, USEREXIST, LOGIN, REGISTER, AUTHORITY, CHANGEPSW, DELETEUSER, LOGINRESULT, REGISTERRESULT, AUTHORITYRESULT, CHANGEPSWRESULT, DELETEUSERRESULT, SERVICEMESSAGE, MESSAGELIST, MESSAGELISTRESULT, NODEEXAMINE, NODERECOVER, FILTERLIST, FILTERLISTSIZE, SERVICEADDRESSINFO, AUTOSERVICE, dkyserviceinfo, dkyservicelist, ServiceCallStatistics, ServiceDelayStatistics, SENDADAPTERPARAM, SENDADAPTEROK, SENDADAPTERFILE, AddServiceComp, AddTransformer, AddTransformerResult, ServicesDep, compileFile, Node_ServiceInfo, SingleServiceStatus, ServiceInterface

}
