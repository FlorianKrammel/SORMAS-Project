import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

WebUI.callTestCase(findTestCase('Login/partials/LoginActions'), [('Password') : GlobalVariable.gPasswordImportUser
	, ('Username') : GlobalVariable.gUsernameImportUser], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Login/partials/ChangeLanguageToEnglish'), [:], FailureHandling.STOP_ON_FAILURE)