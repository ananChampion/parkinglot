package jmu.mapper;

import jmu.vo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    public List<Notice> queryNotice();
    public Notice selectByNId(Integer nId);
    public Integer insertNotice(Notice notice);
    public Integer deleteByNId(Integer nId);
    public Integer updateNotice(Notice notice);
    public Integer insertNoticeForUser(Notice notice);
    public List<Notice> selectByNUId(Integer nUId);

}
