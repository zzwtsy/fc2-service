package cn.zzwtsy.fc2service.dao.repository;

import cn.zzwtsy.fc2service.dao.entity.Seller
import org.springframework.data.repository.CrudRepository

interface SellerRepository : CrudRepository<Seller, Int> {
}