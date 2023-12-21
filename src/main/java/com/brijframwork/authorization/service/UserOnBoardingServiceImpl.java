package com.brijframwork.authorization.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.model.onboarding.EOUserOnBoarding;
import com.brijframwork.authorization.repository.UserOnBoardingRepository;

@Service
public class UserOnBoardingServiceImpl implements UserOnBoardingService {
	

	@Autowired
	private UserOnBoardingRepository userOnBoardingRepository;

	@Override
	public void initOnBoarding(EOUserAccount eoUserAccount) {
		if(eoUserAccount==null) {
			return;
		}
		Map<String, EOUserOnBoarding> onBoardingMap = eoUserAccount.getOnBoardingList().parallelStream().collect(Collectors.toMap((eoUserOnBoarding)->getOnBoardingKey(eoUserOnBoarding.getRoleMenuItem(), eoUserOnBoarding.getUserAccount()), Function.identity()));
		List<EORoleMenuItem> roleMenuItems = eoUserAccount.getUserRole().getRoleMenuItems();
		for(EORoleMenuItem eoRoleMenuItem:  roleMenuItems) {
			String onBoardingKey = getOnBoardingKey(eoRoleMenuItem, eoUserAccount);
			if(onBoardingMap.containsKey(onBoardingKey)) {
				continue;
			}
			if(eoRoleMenuItem.getMenuItem().getOnBoarding()) {
				EOUserOnBoarding eoUserOnBoarding=new EOUserOnBoarding();
				eoUserOnBoarding.setUserAccount(eoUserAccount);
				eoUserOnBoarding.setRoleMenuItem(eoRoleMenuItem);
				userOnBoardingRepository.save(eoUserOnBoarding);
			}
		}
	}

	private String getOnBoardingKey(EORoleMenuItem roleMenuItem, EOUserAccount userAccount) {
		return roleMenuItem.getId()+"_"+userAccount.getId();
	}
}
