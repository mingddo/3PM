<template>
  <div
    class="recommend_card"
    :style="{
      background: 'url(' + this.imgsrc + ')',
      backgroundSize: 'cover',
    }"
  >
    <div class="recommend_contentFrame">
      <div class="group_info">
        <div class="group_title">{{ op.name }}</div>
        <div>
          <i class="fas fa-users" style="cololr:white"></i>
          {{ member }}
        </div>
      </div>
      <div class="groupIntro">{{ hashtag }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    op: Object,
  },
  data() {
    return {
      hashtag: "#빵빵덕 #오리 #볼빵빵 #빵빵덕 #두마리 #모자쓴빵빵덕",
      member: 15,
      imgsrc:
        "https://i.pinimg.com/736x/bf/73/2e/bf732eebccc9c03c026551a87c67886a.jpg",
    };
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.recommend_card {
  width: 400px;
  height: 350px;
  margin: auto;
  z-index: 100;
  /* padding: 10px; */
  /* background-image: url("https://lh3.googleusercontent.com/proxy/PcujSRRpYc-h3fyW1hqxWQmtApbNJ3dA2FQv85P125rLnMgARRzSzuRl4e5tXmSSIaQkjYHOW6xSGvQ0MyM1Zbcn_i2bTAXB7-UjnnYW_7WPNwJzX1T9nX-vLuOyudX2kYygjnelZ7YrZuTnEgY8NUQ0Qm_34_7oNT39jdsfuFhF_P_2gwrJXtG2UPQTz_gAFBrx005hvXGlC7LhTr8CjSVFfaH98_daLtZQJEss-w_7BcGL3lYnR08wQg0g3u4vLgbQNVcJUOIk8J5Y2OVMgpPVLer51sqNjw4WLTsRQpZCDwrBCNHckAFfP8V7OE3OYmUrNlJFg48tvnDEwJSZCc1lQUm-jJIeVsOlfq9WlVn_2gOl581806mivYgoLMZHzYXzE4WHAg");
  background-color: azure;
  background-size: cover; */
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
}
.recommend_contentFrame {
  width: 100%;
  height: 100px;
  margin: 0 auto;
  color: white;
  text-align: center;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  padding: 15px;
  background: linear-gradient(
    to bottom,
    rgba(0, 0, 0, 0),
    40%,
    rgba(0, 0, 0, 0.85)
  );
}
.group_info {
  width: 100%;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: baseline;
}

.group_title {
  font-size: 20px;
  /* font-weight: 600; */
}
.groupIntro {
  width: 100%;
  height: 20px;
  font-size: 15px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
