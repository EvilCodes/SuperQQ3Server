package com.ucai.superqq.biz;

import com.ucai.superqq.bean.ContactBean;
import com.ucai.superqq.bean.GroupBean;
import com.ucai.superqq.bean.UserBean;
/**
 * servlet层与业务逻辑层的接口
 * @author yao
 *
 */
public interface ISuperQQBiz {

	/**
	 * 注册
	 * @param user：用户信息
	 * @return：true：注册成功
	 */
	boolean register(UserBean user) throws Exception;
	
	/**
	 * 取消注册
	 * @param userName:账号
	 * @return
	 * @throws Exception
	 */
	boolean unRegister(String userName);
	
	/**
	 * 登陆
	 * @param userName：账号
	 * @param password：密码
	 * @return 用户完整信息
	 * @throws Exception
	 */
	UserBean login(String userName,String password) throws Exception;
	
	/**
	 * 通过账号查询用户
	 * @param userName:查询条件：账号
	 * @return 用户完整信息
	 */
	UserBean findUserByUserName(String userName);
	
	/**
	 * 查询符合条件的所有用户，支持模糊查询
	 * @param userName：账号，查询条件
	 * @param pageId：页号
	 * @param pageSize：每页返回的记录数
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查询包含nick的所有用户
	 * @param nick：昵称，查询条件
	 * @param pageId：页号
	 * @param pageSize：每页返回的记录数
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	
	/**
	 * 查询不包含userName的所有用户，查询附近人时使用
	 * @param userName:查询条件
	 * @param pageId：页号
	 * @param pageSize：每页返回的记录数
	 * @return
	 */
	UserBean[] findUsers4Location(String userName,int pageId,int pageSize);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	boolean updateUser(UserBean user);
	
	/**
	 * 查询联系人信息
	 * @param myuid：查询条件，当前用户id
	 * @param cuid：查询条件，联系人id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	
	/**
	 * 查询账号是userName的所有联系人
	 * @param userName：查询条件，当前用户的账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查询指定id用户的所有联系人信息
	 * @param myuid：当前用户的id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);
	
	/**
	 * 添加联系人信息
	 * @param userName：当前用户账号
	 * @param name：联系人账号
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	
	/**
	 * 删除联系人
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	
	/**
	 * 修改联系人信息
	 * @param myuid
	 * @param cuid
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
	 * 查询所有的群
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
	 * @param groupName:群名
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
	 * 添加群成员,群组已创建时使用
	 * @param groupName：群名
	 * @param memberName：成员账号
	 * @return
	 */
	boolean addGroupMembers(String groupName,String memberName);
	
	/**
	 * 添加群成员,创建群组时使用
	 * @param groupName：群名
	 * @param memberName：成员账号
	 * @return
	 */
	GroupBean addGroupMember(String groupName,String memberName);
	
	/**
	 * 删除群成员，T群或群成员自动退出群
	 * @param groupName：群名
	 * @param memberName：被T(或自动退群)成员的账号
	 * @return
	 */
	boolean deleteGroupMemeber(String groupName,String memberName);
	/**
	 * 修改组图片主文件名
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateGroupAvatar(String name, String avatar);

}
