select sum(total) total,sum(total_out_house) stotal,sum(smoney) gathered
from gathering  
where (states='待收款' or states='有欠款' or states='预收款' )

<#if coname??>
	and coname like '%${coname}%'
</#if>

<#if sdate??>
	and sjskdate >= '${sdate}'
</#if>

<#if edate??>
	and sjskdate <= '${edate}'
</#if>
