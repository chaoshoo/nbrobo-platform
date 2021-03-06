package com.nky.action.basedata;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.nky.entity.basedata.MessageTemplateEntity;
import com.sys.action.BaseAction;
import com.sys.entity.bo.AjaxPage;
import com.sys.entity.bo.Data;
import com.sys.entity.bo.ScriptPage;
import com.sys.jfinal.JFinalDb;

/**
 * 消息模板.
 * @author Ken
 * @version 2016年9月8日 下午9:42:36
 */
@Controller
@RequestMapping(value = "/messagetemplate")
public class MessageTemplateAction extends BaseAction {

	@RequestMapping(value = "/show")
	public String show(HttpServletRequest request) {
		return "basedata/messagetemplatelist";
	}

	/**
	 * 获取列表
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ScriptPage list(HttpServletRequest request, AjaxPage ajaxPage) {
		ScriptPage scriptPage = null;
		Map<String, Object> param = getParam(request);
		try {
			//权限限制
			param.put("EQ-creator",getSession().getId());
			param.put("EQ-creator_type",getSession().getRoles());
			
			scriptPage = JFinalDb.findPageBySqlid(ajaxPage.getPageNo(), ajaxPage.getPageSize(), "message_template_list",
					param, " create_time asc ");
		} catch (Exception e) {
			LOG.error("查询列表失败.", e);
			scriptPage = new ScriptPage();
		}
		return scriptPage;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Data save(HttpServletRequest request, MessageTemplateEntity entity) {
		Data d = new Data();
		try {
			if (null == entity || StringUtils.isEmpty(entity.getTitle()) || StringUtils.isEmpty(entity.getContent())
					|| null == entity.getMsg_type()) {
				d.setCode(0);
				d.setMsg("添加失败，数据不合法！");
				return d;
			}
			
			if(entity.getTitle().length()>50){
				d.setCode(0);
				d.setMsg("添加失败，标题过长！");
				return d;
			}
			
			Integer loginUserId = getSession().getId();
			//			Integer type = Integer.valueOf(getSession().getRoles()); //type 同role 1	超级管理员 2	医院 3	医生
			Record record = Db.findFirst(
					"select id from message_template where creator = ? and creator_type = ? and  title= ? ",
					loginUserId, getSession().getRoles(), entity.getTitle());
			if (record != null) {
				d.setCode(0);
				d.setMsg("添加失败，该模板名已经使用过！");
				return d;
			} else {
				entity.setCreate_time(new Date());
				entity.setCreator(Long.valueOf(loginUserId));
				entity.setCreator_type(getSession().getRoles());
				boolean flag = JFinalDb.save(entity);
				if (flag) {
					d.setCode(1);
					return d;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		d.setCode(0);
		d.setMsg("保存失败，请联系系统管理员");
		return d;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(HttpServletRequest request, MessageTemplateEntity entity) {
		Data d = new Data();
		try {
			if (null == entity || null == entity.getId() || StringUtils.isEmpty(entity.getTitle())
					|| StringUtils.isEmpty(entity.getContent()) || null == entity.getMsg_type()) {
				d.setCode(0);
				d.setMsg("修改失败，数据不合法！");
				return d;
			}
			
			if(entity.getTitle().length()>50){
				d.setCode(0);
				d.setMsg("添加失败，标题过长！");
				return d;
			}
			Record record = Db.findFirst("select * from message_template where id = ?", entity.getId());
			if (record == null) {
				d.setCode(0);
				d.setMsg("修改失败，请求非法！");
				return d;
			}

			String title = record.getStr("TITLE");
			Timestamp createTime = record.getTimestamp("CREATE_TIME");
			if (!title.equals(entity.getTitle())) {
				//check code unique
				Integer loginUserId = getSession().getId();
				//				Integer type = Integer.valueOf(getSession().getRoles()); //type 同role 1	超级管理员 2	医院 3	医生
				Record recordDuplicate = Db.findFirst(
						"select id from message_template where creator = ? and creator_type = ? and  title= ? ",
						loginUserId, getSession().getRoles(), entity.getTitle());
				if (recordDuplicate != null) {
					d.setCode(0);
					d.setMsg("修改失败，编码已经使用过！");
					return d;
				}
			}

			entity.setCreate_time(new Date(createTime.getTime()));
			entity.setCreator(Long.valueOf(getSession().getId()));
			entity.setCreator_type(getSession().getRoles());
			boolean flag = JFinalDb.update(entity);
			if (flag) {
				d.setCode(1);
				return d;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		d.setCode(1);
		d.setMsg("修改失败，请联系系统管理员");
		return d;
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	//	@RequestMapping(value = "/del") 
	@ResponseBody
	public Data del(HttpServletRequest request, Integer id) {
		Data d = new Data();
		//		String code = request.getParameter("code");
		if (null != id) {
			int x = Db.update("delete from message_template where id=? and creator = ? and creator_type=?", id
					,getSession().getId(),getSession().getRoles());
			if (x > 0) {
				d.setCode(1);
			} else {
				d.setCode(0);
				d.setMsg("删除失败");
			}
		} else {
			d.setCode(0);
			d.setMsg("删除失败,数据不全");
		}
		return d;
	}

}
