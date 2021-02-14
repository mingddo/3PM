<template>
  <div class="activityComponentFrame" @click="onClickActivity(activity.type)">
    <div class="profileImgFrame">
      <img
        v-if="activity.other.img"
        class="pofileImg"
        :src="
          `https://dtbqjjy7vxgz8.cloudfront.net/${activity.other.img}`
        "
        alt="프로필사진"
      />
      <img
        v-else
        class="pofileImg"
        src="http://www.topstarnews.net/news/photo/201903/598316_280802_2053.jpg"
        alt="프로필사진"
      />
    </div>
      <div v-if="activity.type === 1" class="activityContent">
        {{activity.other.nickname}}님이 회원님의 소식을 구독하기 시작했습니다.
      </div>
      <div v-if="activity.type === 2" class="activityContent">
        {{activity.other.nickname}}님이 회원님의 게시물에 박수를 쳤습니다.
      </div>
      <div v-if="activity.type === 3" class="activityContent">
        {{activity.other.nickname}}님이 댓글을 남겼습니다. : {{activity.commentContent}}
      </div>

      <!-- <div>{{ activity }}</div> -->
  </div>
</template>

<script>
export default {
  props: {
    activity: Object,
  },
  methods : {
    

    onClickActivity(type) {
        // type 1 팔로우
        // type 2 게시물 좋아요
        // type 3 댓글
      if (type === 1) {
        // 팔로우하기, 그럼 클릭했을때는 상대방 프로필로 가야한다
        this.$router.push({ name: 'MyPage', query: { name: this.activity.other.id}})
      }
      else {
        // 게시물을 좋아요 댓글, 그럼 클릭했을때는 해당 게시물로 가야한다
        let category = this.activity.category
        const feedId = this.activity.feedId
        switch(category) {
          case 'Indoor':
            category = 1
            break
          default:
            category = 1
        }
        this.$router.push({ name: 'NewsfeedDetail', query: { id: feedId, Category: category } })
      }
    },
  }
};
</script>

<style scoped>
.profileImgFrame {
  width : 100px;
  height: 100px;
}
.activityContent {
  width: 80%;
  height: 70px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  margin-left: 10px;
  line-height: 1.4;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  overflow: hidden;
}
</style>