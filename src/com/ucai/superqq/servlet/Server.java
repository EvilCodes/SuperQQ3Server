package com.ucai.superqq.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ucai.superqq.bean.ContactBean;
import com.ucai.superqq.bean.GroupBean;
import com.ucai.superqq.bean.MessageBean;
import com.ucai.superqq.bean.UserBean;
import com.ucai.superqq.biz.ISuperQQBiz;
import com.ucai.superqq.biz.SuperQQBiz;
import com.ucai.superqq.utils.FileUtils;
import com.ucai.superqq.utils.Utils;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ISuperQQBiz biz;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Server() {
		super();
		biz = new SuperQQBiz();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestType = request.getParameter(I.KEY_REQUEST);
		if (requestType == null) {
			return;
		}
		switch (requestType) {
		case I.REQUEST_REGISTER:
			register(request, response);
			break;
		case I.REQUEST_UNREGISTER:
			unRegister(request, response);
			break;
		case I.REQUEST_LOGIN:
			login(response, request);
			break;
		case I.REQUEST_DOWNLOAD_AVATAR:
		case I.REQUEST_DOWNLOAD_GROUP_AVATAR:
			downloadAvatar(requestType, request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACTS:
			downloadContacts(request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACT_LIST:
			downloadContactList(request, response);
			break;
		case I.REQUEST_ADD_CONTACT:
			addContact(request, response);
			break;
		case I.REQUEST_DELETE_CONTACT:
			deleteContact(request, response);
			break;
		case I.REQUEST_FIND_USER:
			findUserByUserName(request, response);
			break;
		case I.REQUEST_UPLOAD_LOCATION:
			uploadLocation(request, response);
			break;
		case I.REQUEST_DOWNLOAD_LOCATION:
			downloadLocation(request, response);
			break;
		case I.REQUEST_ADD_GROUP_MEMBER:
			addGroupMember(request, response);
			break;
		case I.REQUEST_ADD_GROUP_MEMBERS:
			addGroupMembers(request, response);
			break;
		case I.REQUEST_UPDATE_GROUP_NAME:
			updateGroupName(request, response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_MEMBERS:
			downloadGroupMembers(request, response);
			break;
		case I.REQUEST_DELETE_GROUP_MEMBER:
			deleteGroupMember(request, response);
			break;
		case I.REQUEST_DELETE_GROUP:
			deleteGroup(request, response);
			break;
		case I.REQUEST_DOWNLOAD_GROUPS:
			downloadAllGroups(request, response);
			break;
		case I.REQUEST_FIND_PUBLIC_GROUPS:
			findPublicGroup(request, response);
			break;
		case I.REQUEST_FIND_GROUP:
			findGroupByName(request, response);
			break;
		case I.REQUEST_UPLOAD_AVATAR:
			uploadAvatar(request, response);
			break;
		case I.REQUEST_CREATE_GROUP:
			createGroup(request, response);
			break;
		// case REQUEST_EXIT_GROUP:
		// exitGroup(request,response);
		// break;
		}
	}

	/**
	 * 退群
	 * 
	 * @param request
	 * @param response
	 */
	// private void exitGroup(HttpServletRequest request,
	// HttpServletResponse response) {
	// MessageBean msg=new MessageBean(false, "退群失败");
	// String groupId=request.getParameter(I.Group.GROUP_ID);
	// String userName=request.getParameter(I.User.USER_NAME);
	// boolean isSuccess=biz.exitGroup(groupId,userName);
	// if(isSuccess){
	// msg=new MessageBean(true, "退群成功");
	// }
	// ObjectMapper om=new ObjectMapper();
	// try {
	// om.writeValue(response.getOutputStream(), msg);
	// } catch (JsonGenerationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 通过群名查询群
	 * 
	 * @param request
	 * @param response
	 */
	private void findGroupByName(HttpServletRequest request, HttpServletResponse response) {
		String groupName = request.getParameter(I.Group.NAME);
		GroupBean group = biz.findGroupByGroupName(groupName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取消注册，将账号为userName的用户从服务器删除，包括上传的头像
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void unRegister(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		ObjectMapper om = new ObjectMapper();
		MessageBean msg = new MessageBean(false, "取消注册失败");
		if (userName != null) {
			boolean isSuccess = biz.unRegister(userName);
			if (isSuccess) {
				msg = new MessageBean(true, "取消注册成功");
			}
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端，下载所有公开群
	 * 
	 * @param request
	 * @param response
	 */
	private void findPublicGroup(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		GroupBean[] groups = biz.findPublicGroup(userName, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), groups);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 响应客户端请求，下载当前用户所属的所有群
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadAllGroups(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		GroupBean[] groups = biz.findAllGroup(userName);

		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), groups);
			System.out.println("下载" + userName + "所属的所有群信息");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端解散指定群的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void deleteGroup(HttpServletRequest request, HttpServletResponse response) {
		MessageBean msg = new MessageBean(false, "解散群失败");
		String groupName = request.getParameter(I.Group.GROUP_NAME);
		boolean isSuccess = biz.deleteGroup(groupName);
		ObjectMapper om = new ObjectMapper();
		try {
			if (isSuccess) {
				msg = new MessageBean(true, "解散群成功");
			}
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端的删除请成员的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void deleteGroupMember(HttpServletRequest request, HttpServletResponse response) {
		String groupName = request.getParameter(I.Group.GROUP_NAME);
		String deleteMemberUserName = request.getParameter(I.Group.MEMBERS);
		boolean isSuccess = biz.deleteGroupMemeber(groupName, deleteMemberUserName);
		MessageBean msg = null;
		if (isSuccess) {
			msg = new MessageBean(true, "删除群成员成功");
		} else {
			msg = new MessageBean(false, "删除群成员失败");
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), isSuccess);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端请求，下载群成员->UserBean[]
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadGroupMembers(HttpServletRequest request, HttpServletResponse response) {
		String groupId = request.getParameter(I.Group.GROUP_ID);
		GroupBean group = biz.findGroupByGroupId(groupId);
		String[] members = group.getMembers().split(",");
		UserBean[] users = new UserBean[0];
		for (String member : members) {
			UserBean user = biz.findUserByUserName(member);
			if (user != null) {
				users = Utils.add(users, user);
			}
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), users);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改群昵称
	 * 
	 * @param request
	 * @param response
	 */
	private void updateGroupName(HttpServletRequest request, HttpServletResponse response) {
		MessageBean msg = new MessageBean(false, "修改群名称失败");
		String groupOldName = request.getParameter(I.Group.GROUP_NAME);
		String groupNewName = request.getParameter(I.Group.NEW_NAME);
		GroupBean group = biz.findGroupByGroupName(groupOldName);
		boolean isSuccess = biz.updateGroupName(group, groupNewName);

		ObjectMapper om = new ObjectMapper();
		try {
			if (isSuccess) {
				msg = new MessageBean(true, "修改群名称成功");
			}
			om.writeValue(response.getOutputStream(), msg);
			String path = I.AVATAR_PATH + "group_icon";
			FileUtils.renameImageFileName(path, groupOldName + ".jpg", groupNewName + ".jpg");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端添加群成员的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void addGroupMembers(HttpServletRequest request, HttpServletResponse response) {
		String groupName = request.getParameter(I.Group.GROUP_NAME);
		String member = request.getParameter(I.Group.MEMBERS);
		boolean isSuccess = biz.addGroupMembers(groupName, member);
		MessageBean msg = null;
		if (isSuccess) {
			msg = new MessageBean(true, "新增组成员成功");
		} else {
			msg = new MessageBean(false, "新增组成员失败");
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端添加群成员的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void addGroupMember(HttpServletRequest request, HttpServletResponse response) {
		String groupName = request.getParameter(I.Group.GROUP_NAME);
		String member = request.getParameter(I.Group.MEMBERS);
		GroupBean group = biz.addGroupMember(groupName, member);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端创建群的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void createGroup(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		MessageBean msg = new MessageBean(false, "创建群失败");
		try {
			GroupBean group = om.readValue(request.getInputStream(), GroupBean.class);
			boolean isSuccess = biz.createGroup(group);
			if (isSuccess) {
				msg = new MessageBean(true, "创建群成功");
			}
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下载除了当前用户之外，所有允许获取位置信息的用户
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadLocation(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		UserBean[] users = biz.findUsers4Location(userName, pageId, pageSize);

		if (users != null) {
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), users);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 接收客户端上传的当前用户的位置信息
	 * 
	 * @param request
	 * @param response
	 */
	private void uploadLocation(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int id = Integer.parseInt(request.getParameter(I.User.ID));
		double latitude = Double.parseDouble(request.getParameter(I.User.LATITUDE));
		double longitude = Double.parseDouble(request.getParameter(I.User.LONGITUDE));

		UserBean user = biz.findUserByUserName(userName);
		user.setLatitude(latitude);
		user.setLongitude(longitude);
		boolean isSuccess = biz.updateUser(user);

		ObjectMapper om = new ObjectMapper();
		if (isSuccess) { // 修改所有cuid是id的contact表中记录信息
			ContactBean contact = new ContactBean();
			contact.setCuid(id);
			// 允许myuid查看cuid的位置信息
			contact.setGetMyLocation(true);
			isSuccess = biz.updateContact(contact);
		}
		MessageBean msg = new MessageBean(true, "上传位置失败");
		if (isSuccess) {
			msg = new MessageBean(true, "上传位置成功");
		}
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端查询联系人的请求，返回指定账号的联系人
	 * 
	 * @param request
	 * @param response
	 */
	private void findUserByUserName(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		UserBean user = biz.findUserByUserName(userName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 响应客户端的请求，删除联系人
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private void deleteContact(HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		int myuid = Integer.parseInt(request.getParameter(I.Contact.MYUID));
		int cuid = Integer.parseInt(request.getParameter(I.Contact.CUID));
		boolean isSuccess = biz.deleteContact(myuid, cuid);
		ObjectMapper om = new ObjectMapper();
		MessageBean msg;
		if (isSuccess) {
			msg = new MessageBean(true, "删除联系人成功");
		} else {
			msg = new MessageBean(false, "删除联系人失败");
		}
		System.out.println(msg.getMsg());
		om.writeValue(response.getOutputStream(), msg);
	}

	/**
	 * 响应客户端上传联系人的请求
	 * 
	 * @param request
	 * @param response
	 */
	private void addContact(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		String name = request.getParameter(I.Contact.NAME);
		ContactBean contact = biz.addContact(userName, name);
		if (contact != null) {
			contact = biz.addContact(name, userName);
		}
		if (contact == null) {
			contact = new ContactBean();
			contact.setResult("failure");
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), contact);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下载ArrayLisr<UserBean>
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadContactList(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		UserBean user = biz.findUserByUserName(userName);
		UserBean[] users = biz.findContactListByMyuid(user.getId(), pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), users);
			for (UserBean user2 : users) {
				System.out.println(user2.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 响应客户端要求，下载指定数目的联系人->ContacBean[]
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadContacts(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		ContactBean[] contacts = biz.findContactsByUserName(userName, pageId, pageSize);
		if (contacts != null) {
			ObjectMapper om = new ObjectMapper();
			try {
				om.writeValue(response.getOutputStream(), contacts);
				for (ContactBean contact : contacts) {
					System.out.println(contact.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 响应用户的头像下载
	 * 
	 * @param request
	 * @param response
	 */
	private void downloadAvatar(String requestType, HttpServletRequest request, HttpServletResponse response) {
		File file = null;
		String avatar = request.getParameter(I.User.AVATAR);
		file = new File(I.AVATAR_PATH + avatar);

		if (!file.exists()) {
			System.out.println("头像不存在");
			return;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			int len;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			System.out.println("头像下载完毕");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 响应用户的登陆
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private void login(HttpServletResponse response, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		String userName = request.getParameter(I.User.USER_NAME);
		String password = request.getParameter(I.User.PASSWORD);
		ObjectMapper om = new ObjectMapper();
		UserBean user = null;
		try {
			user = biz.login(userName, password);
			user.setResult("ok");
			System.out.println("登陆成功");
		} catch (Exception e) {
			user = new UserBean();
			user.setResult("faiure");
			System.out.println("登陆失败");
		}
		om.writeValue(response.getOutputStream(), user);
	}

	/**
	 * 响应客户端的注册请求
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) {
		// 步骤1-从reqeust中获取userName、nick和password
		String userName = request.getParameter(I.User.USER_NAME);
		String nick = request.getParameter(I.User.NICK);
		// 解决乱码问题
		try {
			nick = new String(nick.getBytes(I.ISON8859_1), I.UTF_8);
			String password = request.getParameter(I.User.PASSWORD);
			// 步骤2-将三个数据封装在一个UserBean对象中
			UserBean user = new UserBean(userName, nick, password);
			ObjectMapper om = new ObjectMapper();
			// 步骤3-调用业务逻辑层的方法进行注册
			boolean isSuccess = biz.register(user);
			MessageBean msg = null;
			// 步骤4、将isSuccess发送给客户端
			if (isSuccess) {
				msg = new MessageBean(true, "注册成功");
			} else {
				msg = new MessageBean(false, "注册失败");
			}
			om.writeValue(response.getOutputStream(), msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
/*		String requestType = request.getParameter(I.KEY_REQUEST);
		switch (requestType) {
		case I.REQUEST_UPLOAD_AVATAR:
			uploadAvatar(request, response);
			break;
		case I.REQUEST_CREATE_GROUP:
			createGroup(request, response);
			break;
		case I.REQUEST_LOGIN:
			login(response, request);
			break;
		case I.REQUEST_REGISTER:
			register(request, response);
			break;
		}
*/	}

	/**
	 * 上传头像
	 * 
	 * @param dis
	 * @param response
	 * @throws IOException
	 */
	private void uploadAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String userName = request.getParameter(I.User.USER_NAME);
		String typeAvatar = request.getParameter(I.AVATAR_TYPE);
		String fileName = request.getParameter(I.Avatar.FILE_NAME);
		System.out.println(typeAvatar);
		System.out.println(fileName);
		System.out.println("userName="+userName);
		
		String path = I.AVATAR_PATH + typeAvatar;// 客户端单独注册、上传头像测试用的目录
		System.out.println(path);
		File file = new File(path, fileName);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024 * 8];
		int len;
		while ((len = request.getInputStream().read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		boolean isSuccess = false;
		switch (typeAvatar) {
		case "group_icon":
			GroupBean group = biz.findGroupByGroupName(userName);
			group.setAvatar(typeAvatar + "/" + fileName);
			isSuccess = biz.updateGroupAvatar(group.getName(), group.getAvatar());
			break;
		case "user_avatar":
		default:
			UserBean user = biz.findUserByUserName(userName);
			user.setAvatar(typeAvatar + "/" + fileName);
			isSuccess = biz.updateUser(user);
		}

		ObjectMapper om = new ObjectMapper();
		if (isSuccess) {
			om.writeValue(response.getOutputStream(), new MessageBean(true, "上传头像成功"));
		} else {
			om.writeValue(response.getOutputStream(), new MessageBean(false, "上传头像失败"));

		}
		System.out.println("头像上传成功，路径:" + path + "/" + fileName);
		fos.close();
	}

}
