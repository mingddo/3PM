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
      <div v-if="activity.type === 4" class="activityContent">
        {{activity.other.nickname}}님이 {{activity.group.name}}에 가입하셨습니다.
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
        // type 4 그룹
      if (type === 1) {
        // 팔로우하기, 그럼 클릭했을때는 상대방 프로필로 가야한다
        this.$router.push({ name: 'MyPage', query: { name: this.activity.other.id}}).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
      }
      else if (type === 4 ) {
        this.$router.push({ name : 'groupdetail', query: { groupId: this.activity.group.id}})
      }
      else {
        // 게시물을 좋아요 댓글, 그럼 클릭했을때는 해당 게시물로 가야한다
        let category = this.activity.category
        const feedId = this.activity.feedId
        switch(category) {
          case 'Indoor':
            category = 1
            break
          case 'Insider':
            category = 2
            break;
          case 'Outdoor':
            category = 3
            break;
          case 'Worker':
            category = 4
            break;
        }
        this.$router.push({ name: 'NewsfeedDetail', query: { id: feedId, Category: category } })
      }
    },
  }
};
</script>

<style scoped>
.profileImgFrame {
  width : 60px;
  height: 60px;
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
.activityComponentFrame {
  cursor: pointer;
  background-color: var(--white-color);
  width: 100%;
  margin: auto;
  padding: 15px 0;
  display: flex;
  box-shadow: 0 1px 2px rgb(0 0 0 / 20%);
  border-radius: 10px;
  margin-bottom: 10px;
}
</style>