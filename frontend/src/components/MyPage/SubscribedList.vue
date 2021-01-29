<template>
  <div class="SubscribedComponent">
    <header class="myPageActivityHeader">
      <h2>구독자</h2>
    </header>
    <hr class="myPageActivityhr" />
    <div class="subscribedContentFrame">
      <h3>구독 목록</h3>
      <div class="subscribedContent">
        <SubscribedUser
          v-for="(subscriber, idx) in subscribedlist"
          :key="idx"
          :subscriber="subscriber"
        />
      </div>
    </div>
  </div>
</template>

<script>
import SubscribedUser from "@/components/MyPage/SubscribedUser.vue";
export default {
  components: { SubscribedUser },
  props: {
    subscribedlist: Array,
  },
};
</script>

<style scoped>
.SubscribedComponent {
  width: 100%;
}
.myPageActivityHeader {
  width: 90%;
  margin: auto;
  /* padding: 5px 50px; */
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.myPageActivityhr {
  width: 90%;
}

.myPageActivityHeaderMoreInfo {
  width: 70px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.fa-angle-right {
  font-size: 30px;
}

.subscribedContentFrame {
  width: 90%;
  padding: 15px;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
}

.subscribedContent {
  width: 100%;
  margin: 10px;
  padding: 15px 0;
  display: flex;
  flex-wrap: wrap;
  /* flex-direction: column;
  justify-content: center;
  align-items: flex-start; */
}
</style>
