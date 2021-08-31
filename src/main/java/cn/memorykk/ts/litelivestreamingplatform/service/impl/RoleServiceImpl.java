package cn.memorykk.ts.litelivestreamingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.memorykk.ts.litelivestreamingplatform.dao.RoleDao;
import cn.memorykk.ts.litelivestreamingplatform.entity.Role;
import cn.memorykk.ts.litelivestreamingplatform.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}
