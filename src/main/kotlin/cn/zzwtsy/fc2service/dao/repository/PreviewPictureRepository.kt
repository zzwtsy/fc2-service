package cn.zzwtsy.fc2service.dao.repository;

import cn.zzwtsy.fc2service.dao.entity.PreviewPicture
import org.springframework.data.repository.CrudRepository

interface PreviewPictureRepository : CrudRepository<PreviewPicture, Int> {
}