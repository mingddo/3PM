<template>
  <div class="SubscribedComponent">

    <div class="subscribedContentFrame">
      <h3>구독 목록</h3>
      <div class="subscribedContent" v-if="subscribedlist.length">
        <SubscribedUser
          v-for="(subscriber, idx) in subscribedlist"
          :key="idx"
          :subscriber="subscriber"
          @decrement="deleteSubscribe"
          
        />
      </div>
      <div v-else class="subscribedContent">
        <div class="subscribedComponentFrame">구독하는 사람이 없습니다.</div>
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
  methods : {
    deleteSubscribe() {
      this.$emit('decrement')
    }
  }
};
</script>

<style scoped>
.SubscribedComponent {
  width: 100%;
}
.myPageActivityHeader {
  width: 100%;
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
  width: 100%;
  padding: 15px;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
}

.subscribedContent {
  width: 100%;
  padding: 15px 0;
  display: flex;
  flex-wrap: wrap;
  /* flex-direction: column;
  justify-content: center;
  align-items: flex-start; */
}
.subscribedComponentFrame {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom : 10px;
  padding: 10px;
  border: none;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background-color: var(--white-color);
}
</style>
