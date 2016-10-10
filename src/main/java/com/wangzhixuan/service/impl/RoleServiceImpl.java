package com.wangzhixuan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangzhixuan.commons.result.Tree;
import com.wangzhixuan.commons.utils.PageInfo;
import com.wangzhixuan.mapper.RoleMapper;
import com.wangzhixuan.mapper.RoleResourceMapper;
import com.wangzhixuan.mapper.UserRoleMapper;
import com.wangzhixuan.model.Role;
import com.wangzhixuan.model.RoleResource;
import com.wangzhixuan.service.IRoleService;
import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    
    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return userRoleMapper.selectRoleIdListByUserId(userId);
    }

    @Override
    public List<Map<Long, String>> selectRoleResourceListByRoleId(Long roleId) {
        return roleMapper.selectResourceListByRoleId(roleId);
    }

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object selectTree() {
        List<Tree> trees = new ArrayList<Tree>();
        List<Role> roles = roleMapper.selectAll();
        for (Role role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId());
            tree.setText(role.getName());

            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }

    @Override
    public void updateRoleResource(Long id, String resourceIds) {
        // 先删除后添加,有点爆力
        List<Long> roleResourceIdList = roleMapper.selectResourceIdListByRoleId(id);
        if (roleResourceIdList != null && (!roleResourceIdList.isEmpty())) {
            for (Long roleResourceId : roleResourceIdList) {
                roleResourceMapper.deleteById(roleResourceId);
            }
        }
        String[] resources = resourceIds.split(",");
        RoleResource roleResource = new RoleResource();
        for (String string : resources) {
            roleResource.setRoleId(id);
            roleResource.setResourceId(Long.parseLong(string));
            roleResourceMapper.insert(roleResource);
        }
    }

}