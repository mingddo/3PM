<template>
  <div class="newsfeed">
    <GroupFeedHeader class="newsfeed-header"/>
    <section v-if="fd" v-cloak class="feed-detail">
      <div class="feed-detail-userprofile-box">
        <img
          src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
          alt="유저프로필이미지"
          class="feed-detail-userprofile-img"
          @click="goToProfile"
        >
        <div class="feed-detail-userprofile-content">
          <div>
            <h3 v-if="fd.user.nickname" class="feed-detail-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</h3>
            <h3 v-else class="feed-detail-userprofile-name" @click="goToProfile">anonymous</h3>
          </div>
          
          <div>
            <p>{{ date }} {{ time }} </p>
          </div>
          
        </div>

      </div>
    </section>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
