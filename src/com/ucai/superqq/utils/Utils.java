package com.ucai.superqq.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	/**
	 * 向数组末尾添加一个元素
	 * @param list
	 * @param t
	 * @return
	 */
	public static <T> T[] add(T[] list,T t){
		if(t!=null){
			list=Arrays.copyOf(list, list.length+1);
			list[list.length-1]=t;
		}
		return list;
	}
	
	/**
	 * 删除members中的deleteMemberUserName字符串，包括deleteMemberUserName前面或后面的逗号
	 * @param members
	 * @param deleteMemberUserName
	 * @return  a,aa,bb,aaa, a,
	 */
//	public static String deleteMember(String members, String deleteMemberUserName) {
//		members+=",";//添加一个逗号
//		//将要删除的账号加一个逗号，从members字符串中删除
//		members = members.replace(deleteMemberUserName+",", "");
//		//将最后多出的逗号删除
//		members=members.substring(0,members.length()-1);
//		return members;
//	}
	
	/**
	 * 删除members中的member字符串，包括member前面或后面的逗号
	 * @param members
	 * @param member
	 */
	public static String deleteMember(String members,String member) {
		Pattern pattern=null;
		Matcher matcher=null;
		//若members中只包含member一个字符串，则直接返回空字符串
		if(members.equals(member)){
			return "";
		}
		pattern=Pattern.compile("\\,"+member+"\\,");
		if(members.startsWith(member+",")){
			members=","+members;
			matcher=pattern.matcher(members);
			members=matcher.replaceAll("");
		}else if(members.endsWith(","+member)){
			members+=",";
			matcher=pattern.matcher(members);
			members=matcher.replaceAll("");
		}else if(members.contains(","+member+",")){//被删除账号位于字符串中间
			matcher=pattern.matcher(members);
			members=matcher.replaceAll(",");
		}
		return members;
	}
	
}
