package com.ucai.superqq.dao;

import com.ucai.superqq.bean.ContactBean;
import com.ucai.superqq.bean.GroupBean;
import com.ucai.superqq.bean.UserBean;

/**
 * 业务逻辑层与数据访问层之间的接口
 * 规定了两层之间的方法调用协议
 * @author yao
 *
 */
public interface ISuperQQDao {

	/**
	 * 根据账号查询用户
	 * @param userName：账号
	 * @return
	 */
	UserBean findUserByUserName(String userName);
	
	/**
	 * 查找所有包含userName的用户
	 * @param userName:账号
	 * @param pageId：页号
	 * @param pageSize：每页查询的记录数
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查找所有昵称中包含nick的用户
	 * @param nick：昵称
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	
	/**
	 * 查找所有允许获取位置信息的用户，除账号为userName之外
	 * @param userName：当前用户的账号
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);
	
	/**
	 * 添加用户，注册用户
	 * @param user
	 * @return
	 */
	boolean addUser(UserBean user);
	
	/**
	 * 删除用户，取消注册使用
	 * @param userName
	 * @return
	 */
	boolean deleteUser(String userName);
	/**
	 * 修改用户，用于修改昵称、头像、所属群等信息
	 * @param user
	 * @return
	 */
	boolean updateUser(UserBean user);
	
	/**
	 * 检查userName的好友name在contac表中是否存在
	 * @param userName：当前用户的账号
	 * @param name：好友的账号
	 * @return
	 */
	boolean isExistsContact(String userName,String name);
	
	/**
	 * 查找当前用户的好友
	 * @param myuid：当前用户的在数据表中的id
	 * @param cuid：好友的id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	
	/**
	 * 查找账号为userName的用户的所有好友
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查找id为myuid的所有好友
	 * @param myuid：
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);
	
	/**
	 * 增加好友
	 * @param userName：当前用户的账号
	 * @param name：好友的账号
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	
	/**
	 * 删除联系人
	 * @param myuid:当前用户id
	 * @param cuid：联系人id
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	
	/**
	 * 修改联系人
	 * @param contac
	 * @return
	 */
	boolean updateContact(ContactBean contact);
	
	/**
	 * 创建群,向groups表插入一条表示群的记录
	 * @param group：群信息
	 * @return:true：创建成功
	 */
	boolean createGroup(GroupBean group);
	
	/**
	 * 修改群信息
	 * @param group：指定群的信息实体
	 * @return：true:修改成功
	 */
	boolean updateGroupName(GroupBean group,String newGroupName);
	
	/**
	 * 删除指定群
	 * @param groupName：群名
	 * @return
	 */
	boolean deleteGroup(String groupName);
	
	/**
	 * 查询账号为userName的所属群
	 * @return
	 */
	GroupBean[] findAllGroup(String userName);
	
	/**
	 * 查找所有公开群
	 * @return
	 */
	GroupBean[] findPublicGroup(String userName,int pageId,int pageSize);
	
	/**
	 * 查询指定群
	 * @param groupName
	 * @return
	 */
	GroupBean findGroupByGroupName(String groupName);

	/**
	 * 查询指定群
	 * @param groupId:环信groupId
	 * @return
	 */
	GroupBean findGroupByGroupId(String groupId);
	
	/**
	 * 添加群成员
	 * @param groupName：群名
	 * @param memberName：成员账号
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);
		
	/**
	 * 删除群成员，T群
	 * @param groupName：群名
	 * @param memberName：被T成员的账号
	 * @return
	 */
	boolean deleteGroupMemeber(String groupName,String memberName);
	
	/**
	 * 查找指定群的所有用户
	 * @param groupName
	 * @return ：所有成员的账号
	 */
	String[] findGroupMembersByGroupName(String groupName);

	/**
	 * 修改user.groups
	 * @param uId
	 * @param groups
	 * @return
	 */
	public boolean updateUser4Group(int uId,String groups);
	
	/**
	 * 修改组图片主文件名
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name, String avatar);

//	/**
//	 * 退群
//	 * @param groupId：群id
//	 * @param userName：退群成员的账号
//	 * @return
//	 */
//	boolean exitGroup(String groupId, String userName);
	
}
