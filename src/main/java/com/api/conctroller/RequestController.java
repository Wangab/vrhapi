package com.api.conctroller;

import com.api.mode.Follows;
import com.api.mode.Likes;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanganbang on 8/2/16.
 */
@RestController
@RequestMapping("/concern")
public class RequestController {
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * 点赞
     * {
     *      "uuid":"121231212",
     *      "eventid":"b3U6ULp-dlfCEeaKCQBQVsAAC5"
     *      "type":"goods/user/shop"
     * }
     *
     * @param like
     * @return
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "点赞接口测试", notes = "输入 和输出为JSON格式", produces = "application/json")
    Likes like(@RequestBody Likes like) {
        mongoOperations.save(like);
        return like;
    }

    /**
     * 收藏
     * {
     *      "uuid":"121231212",
     *      "eventid":"b3U6ULp-dlfCEeaKCQBQVsAAC5"
     *      "type":"goods/user/shop"
     * }
     *
     * @param follows
     * @return
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ApiOperation(value = "收藏接口测试", notes = "输入 和输出为JSON格式", produces = "application/json")
    public
    @ResponseBody
    Follows following(@RequestBody Follows follows) {
        mongoOperations.save(follows);
        return follows;
    }

    /**
     * 统计自身点赞数量
     *
     * @param uuid
     * @param type user\goods\shop
     * @return
     */
    @RequestMapping(value = "/mylikesCount/{type}/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "统计自身点赞数量接口测试", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getLikesCountByUUID(@PathVariable("type") String type, @PathVariable("uuid") String uuid) {
        Long count = mongoOperations.count(new Query(Criteria.where("userid").is(uuid).and("type").is(type)), Likes.class);
        count = count == null ? 0:count;
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("uuid", uuid);
        retmap.put("count", count);
        return retmap;
    }

    /**
     * 查询点赞列表
     * @param uuid 点赞者用户ID
     * @param type 点赞类型，user\goods\shop
     * @return
     */
    @RequestMapping(value = "/mylikes/{type}/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "查询点赞列表", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getLikesByUUID(@PathVariable("uuid") String uuid, @PathVariable("type") String type,@RequestParam int page) {
        Query query = new Query(Criteria.where("userid").is(uuid).and("type").is(type));
        if (page < 1) {
            page = 1;
        }
        query.skip((page-1) * 50);
        query.limit(50);
        List<Likes> list = mongoOperations.find(query, Likes.class);
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("uuid", uuid);
        retmap.put("results", list);
        return retmap;
    }
    /**
     * 统计某个实体被点赞数量
     *
     * @param eventid
     * @return
     */
    @RequestMapping(value = "/likesCount/{eventid}", method = RequestMethod.GET)
    @ApiOperation(value = "统计某个实体被点赞数量", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getLikesCountByEventID(@PathVariable("eventid") String eventid) {
        Long count = mongoOperations.count(new Query(Criteria.where("eventid").is(eventid)), Likes.class);
        count = count == null ? 0:count;
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("eventid", eventid);
        retmap.put("count", count);
        return retmap;
    }

    /**
     * 统计自身收藏的数量
     *
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/myFollowerCount/{type}/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "统计自身收藏的数量", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getFollowsCountByUUID(@PathVariable("type") String type, @PathVariable("uuid") String uuid) {
        Long count = mongoOperations.count(new Query(Criteria.where("userid").is(uuid).and("type").is(type)), Follows.class);
        count = count == null ? 0:count;
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("uuid", uuid);
        retmap.put("count", count);
        return retmap;
    }

    /**
     * 查询收藏列表
     * @param uuid
     * @param type
     * @return
     */
    @RequestMapping(value = "/myFollowers/{type}/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "统计自身收藏的列表", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getFollowsByUUID(@PathVariable("uuid") String uuid, @PathVariable("type") String type,@RequestParam int page) {
        Query query = new Query(Criteria.where("userid").is(uuid).and("type").is(type));
        if (page < 1) {
            page = 1;
        }
        query.skip((page-1) * 50);
        query.limit(50);
        List<Follows> list = mongoOperations.find(query, Follows.class);
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("uuid", uuid);
        retmap.put("results", list);
        return retmap;
    }

    /**
     * 统计某个实体被收藏的数量
     *
     * @param eventid
     * @return
     */
    @RequestMapping(value = "/FollowerCount/{eventid}", method = RequestMethod.GET)
    @ApiOperation(value = "统计某个实体被收藏的数量", notes = "输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> getFollowsCountByEventID(@PathVariable("eventid") String eventid) {
        Long count = mongoOperations.count(new Query(Criteria.where("eventid").is(eventid)), Follows.class);
        count = count == null ? 0:count;
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("eventid", eventid);
        retmap.put("count", count);
        return retmap;
    }
}
