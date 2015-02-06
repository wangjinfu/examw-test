<#--定义宏-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#--头部资源-->
<#macro header_resources>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="http://img.examw.com/test/pub.css" rel="stylesheet" type="text/css" />
<link href="http://img.examw.com/test/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://img.examw.com/jQuery.js"></script>
</#macro>
<#--头部资源－分页脚本-->
<#macro header_resources_paging>
<link href="http://img.examw.com/test/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://img.examw.com/test/jquery.pager.js"></script>
</#macro>
<#--topbox-->
<#macro topbox>
<div class="topbox">
	<div class="main">
        <div class="top-l">
            <ul>
                <li><a class="shy" href="http://www.examw.com/">首页</a></li>
                <li><a href="/ksbd/fdc/">宝典</a></li>
                <li><a href="/zl/class/">网校</a></li>
                <li><a href="http://test.examw.com/91/">模考</a></li>
                <li><a href="http://test.examw.com/91/">论坛</a></li>
                <li class="brno"><a href="http://book.examw.com/fdc/">图书</a></li>
            </ul>
        </div>
        <script type="text/javascript">var LoginAStr = "<div class=\"log\">欢迎您 <a target=\"_blank\" href=\"http://test.examw.com/user/UserCenter\" style=\"color:#cc0000\">$LoginName$</a></div><div class=\"vip fl\"><a href=\"http://test.examw.com/user/UserCenter\">个人中心</a></div>	<div class=\"vip fl\"><a href=\"http://test.examw.com/user/Login/CheckUser.asp?CheckType=Logout\">安全退出</a></div>";</script>
        <div class="top-r" id="LoginA">
        	<div class="log"><a href="http://test.examw.com/class?DoMain=test" target="_blank">登陆</a></div>
            <div class="ge fl">|</div>
            <div class="log"><a href="http://test.examw.com/class?DoMain=test" target="_blank">注册</a></div>
        </div>
    </div>
</div>
<div class="main">
	<div class="logo fl"><a href="http://www.examw.com/"><img src="http://img.examw.com/index/logo.png" width="281" height="92" /></a></div>
    <div class="tel">
        <div class="telnum">4000-525-585</div>
        <div class="teltime">服务时间&nbsp;&nbsp;08：30 - 21：30</div>
    </div>
</div>
<div class="menubox fl bulebg">
	<div class="menu">
        <ul>
            <li class="cur"><a href="/">首页</a></li>
            <li><a href="/#category">考试分类</a></li>
            <li><a href="/news">最新试卷</a></li>
            <li><a href="/hots">试卷排行</a></li>
            <li><a href="/questions/list">常见问题</a></li>
        </ul>
        <#-- 暂时默认的是[一建工程经济产品] -->
        <div class="my"><a href="http://tiku.examw.com/library/da629068-6f9d-4dee-9dd7-52989544a565" target="_blank"></a></div>
    </div>
</div>
</#macro>

<#--最新试卷，最热试卷，常见问题-->
<#macro news_hots_question>
<div class="newbox fl">
	<div class="main">
        <div class="newshj">
        	<#--最新试卷-->
        	<@news_papers />
        </div>
        <div class="newshj">
        	<#--最热试卷-->
        	<@hots_papers />
        </div>
        <#--常见问题-->
        <@questions_list />
    </div>
</div>
</#macro>
<#--试卷列表显示-->
<#macro papers_list_views papers>
<#if (papers??)&&(papers?size>0)>
<ul class="list-shj" id="list-shj">
	<#list papers as data>
    <li>
    	<p>&middot;<a href="${data.category}/${data.id}.html" title="${data.text}"><@text_length_limit data.text/></a></p>
        <p class="btn"><span>${data.total}人参与</span><a href="${data.category}/${data.id}.html">免费测试</a></p>
    </li>
    </#list>
</ul>
</#if>
</#macro>
<#--试卷名称字符串截断 -->
<#macro text_length_limit text>
<#if (text?length >25)>
	${text?substring(0,26)}...
<#else>
	${text}
</#if>
</#macro>
<#--最新试卷-->
<#macro news_papers>
<div class="newtit bulebg fl"><span><a <#if abbr??>href="/news/${abbr}"<#else>href="/news/"</#if> target="_blank">更多&gt;&gt;</a></span>最新试卷</div><div class="newtitbg"></div>
<@papers_list_views newsPapers/>
</#macro>
<#--常见问题列表－最新试卷 -->
<#macro question_list_news_papers>
<div class="tit fl"><b>最新试卷</b></div>
<div class="cont fl">
	<@papers_list_views newsPapers/>
</div>
</#macro>
<#--常见问题明细－最新试卷 -->
<#macro question_detail_news_papers>
<div class="tit fl"><b>最新试卷</b></div>
<div class="cont fl">
	<@papers_list_views newsPapers/>
</div>
</#macro>
<#--最热试卷-->
<#macro hots_papers>
<div class="newtit bulebg fl"><span><a <#if abbr??>href="/hots/${abbr}"<#else>href="/hots/"</#if> target="_blank">更多&gt;&gt;</a></span>热门试卷</div><div class="newtitbg"></div>
<@papers_list_views hotsPapers/>
</#macro>
<#--常见问题列表－最热试卷-->
<#macro question_list_hots_papers>
<div class="tit fl"><b>热门试卷</b></div>
<div class="cont fl">
    <@papers_list_views hotsPapers/>
</div>
</#macro>
<#--常见问题详细－最热试卷-->
<#macro question_detail_hots_papers>
<div class="tit fl"><b>热门试卷</b></div>
<div class="cont fl">
	<@papers_list_views hotsPapers/>
</div>
</#macro>
<#--常见问题-->
<#macro questions_list>
<div class="new-question">
	<div class="newtit bulebg fl"><span><a href="/questions/list/" target="_blank">更多&gt;&gt;</a></span>常见问题</div><div class="newtitbg"></div>
    <#if (questions??)>
    <ul class="list">
    	<#list questions as data>
        <li>&middot;<a href="/questions/${data.id}.html" title="${data.text}"><@text_length_limit data.text/></a></li>
        </#list>
    </ul>
    </#if>
</div>
</#macro>
<#--友情链接-->
<#macro friend_link>
<div class="link">
	<h4><a href="javascript:void(0)">友情链接</a><span>QQ：712931605</span></h4>
	<div class="box fl">$FriendLink$</div>
</div>
</#macro>
<#-- 软件特色 -->
<#macro soft_feature>
<div class="titbox bulebg fl">软件特色</div>
<div class="titboxbg"></div>
<div class="tesebox fl">
	<div class="tese btno">
		<div class="pic fl">
			<img src="http://img.examw.com/test/01.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="one"></div>
			<i>全真模考</i> <span>依托行业最先进智能算法，配合行业最全面考题，试卷严格按照历年考试出题情况、知识点分布情况及今年命题方向分析进行编制，模拟真实考试环境，让考试不再陌生，让你学习考试更简单！</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fr">
			<img src="http://img.examw.com/test/02.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="two"></div>
			<i>智能云数据</i> <span>根据海量用户做题的智能化分析判断数据，分析错题，易错知识点,
				历年真题出题机率，精准预测考点．考题．命题，全维度划定史上最小考试范围，无限接近考试，做的就是最接近考试的试题。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fl">
			<img src="http://img.examw.com/test/03.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="three"></div>
			<i>自由组卷</i> <span>自由组卷，可选题型、难度、错误率，等多种方式。从基础学习阶段，到冲刺复习阶段，再到考前检测阶段，您都能在题库内找到相应的练习，帮助您迅速提高成绩。</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fr">
			<img src="http://img.examw.com/test/04.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="four"></div>
			<i>错题收藏</i> <span>交卷之后可系统自动批改，错题自动记录并且都有详细解析， 便于复习薄弱考点。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fl">
			<img src="http://img.examw.com/test/05.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="five"></div>
			<i>专业答疑</i> <span>学贵知疑，学习疑难问老师，有疑问随时提交，专业答疑老师解决个性化学习难题。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese bbno">
		<div class="pic fr">
			<img src="http://img.examw.com/test/06.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="six"></div>
			<i>终身使用</i> <span>一次购买，终身免费升级，并支持电脑，手机等多平台使用。考试一点通下载安装并注册成功之后，您也可以离线使用它了，充分利用您的碎片化时间进行学习。</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com">立即做题</a></div></em>-->
		</div>
	</div>
</div>
</#macro>
<#--footer-->
<#macro footer>
<div class="footer">
	<div class="h1f"></div>
	<div class="nva"><a href="/about/">关于本站</a>┊<a href="/about/map.htm">网站地图</a>┊<a href="/about/copyright.html">网站声明</a>┊<a href="/about/ads.html">广告服务</a>┊<a href="/link/">友情链接</a>┊<a href="/about/job/">诚聘英才</a>┊<a href="/about/contact.html">联系我们</a>┊<a href="http: //bbs.examw.com/forum-4-1.html">意见咨询</a></div>
	<div id="ft_about"></div>
</div>
<script type="text/javascript" language="javascript" src="http://img.examw.com/e.js"></script>
<script type="text/javascript" language="javascript" src="http://img.examw.com/test/c.js"></script>
<#-- 统计代码  -->
<iframe src="/count.html" height="0" width="0" scrolling="no"></iframe>
</#macro>