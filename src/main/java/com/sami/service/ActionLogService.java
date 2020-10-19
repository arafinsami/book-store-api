package com.sami.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sami.entity.ActionLog;
import com.sami.enums.Action;
import com.sami.enums.ModuleName;
import com.sami.repository.ActionLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActionLogService {

	private final ActionLogRepository actionLogRepository;

	@Transactional
	public void publishActivity(ModuleName moduleName, Action action, String documentId, String comments) {

		ActionLog actionLog = new ActionLog();
		actionLog.setModuleName(moduleName);
		actionLog.setAction(action);
		actionLog.setUserName(actionLog.getUserName());
		actionLog.setDocumentId(documentId);
		actionLog.setComments(comments);
		actionLog.setCreated(new Date());
		actionLogRepository.save(actionLog);
	}

}
