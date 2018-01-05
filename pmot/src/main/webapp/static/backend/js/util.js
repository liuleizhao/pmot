function isNull(str){
	var strObj = new String(str);
	if(strObj==null || strObj==undefined || strObj=="" || strObj=="null" || strObj=="undefined" || strObj.replace(/^\s*$/,"").length==0){
		return true;
	}
	return false;
}
function isNotNull(str){
	return !isNull(str);
}
