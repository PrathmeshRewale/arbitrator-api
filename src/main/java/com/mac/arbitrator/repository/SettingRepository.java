package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting,Long> {
}
