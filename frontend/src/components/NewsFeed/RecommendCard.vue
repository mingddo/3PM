<template>
  <div class="recommend_card">
    <div class="recommend_contentFrame">
      <div class="recommend_preview">
        <div class="tag">
          {{ hashtag }}
        </div>
        <div class="content_preview">
          <div class="imgFrame">
            <!-- <img src="@/assets/loverduck.png" alt="" /> -->
          </div>
          <div class="preview_username">빵빵덕</div>
          <div class="preview_content">
            빵빵덕은 언제부터 귀여웠을까요? 빵빵덕❤ 빵빵덕은 언제부터
            귀여웠을까요? 빵빵덕❤ 빵빵덕은 언제부터 귀여웠을까요? 빵빵덕❤
            빵빵덕은 언제부터 귀여웠을까요? 빵빵덕❤ 빵빵덕은 언제부터
            귀여웠을까요? 빵빵덕❤ 빵빵덕은 언제부터 귀여웠을까요?빵빵덕은
            언제부터 귀여웠을까요?
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hashtag: "#빵빵덕 #오리 #볼빵빵 #빵빵덕",
    };
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.preview_username {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 10px;
}

.preview_content {
  font-size: 15px;
  margin: 16px auto;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
  height: 80px;
  width: 90%;
  white-space: normal;
  word-break: break-word;
}

.recommend_card {
  width: 400px;
  height: 300px;
  margin: auto;
  z-index: 100;
  background-image: url("https://lh3.googleusercontent.com/proxy/PcujSRRpYc-h3fyW1hqxWQmtApbNJ3dA2FQv85P125rLnMgARRzSzuRl4e5tXmSSIaQkjYHOW6xSGvQ0MyM1Zbcn_i2bTAXB7-UjnnYW_7WPNwJzX1T9nX-vLuOyudX2kYygjnelZ7YrZuTnEgY8NUQ0Qm_34_7oNT39jdsfuFhF_P_2gwrJXtG2UPQTz_gAFBrx005hvXGlC7LhTr8CjSVFfaH98_daLtZQJEss-w_7BcGL3lYnR08wQg0g3u4vLgbQNVcJUOIk8J5Y2OVMgpPVLer51sqNjw4WLTsRQpZCDwrBCNHckAFfP8V7OE3OYmUrNlJFg48tvnDEwJSZCc1lQUm-jJIeVsOlfq9WlVn_2gOl581806mivYgoLMZHzYXzE4WHAg");
  background-color: azure;
  background-size: cover;
  border-radius: 5px;
}
.recommend_contentFrame {
  width: auto;
  height: 100%;
  /* margin: 5px; */
  position: relative;
}

.screen {
  position: relative;
  overflow: hidden;
}

.recommend_preview {
  position: absolute;
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(57, 58, 58, 0.9);
  bottom: 0%;
  font-size: 14px;
  text-align: center;
  padding: 20px;
  color: rgb(236, 234, 234);
  transition: all 0.35s;
  border-radius: 5px;
  overflow: hidden;
}
.tag {
  width: 200px;
  height: 20px;
  margin: auto;
  font-size: 15px;
  font-weight: 600;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.content_preview {
  position: absolute;
  top: 150%;
  width: 90%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.imgFrame {
  width: 60px;
  height: 60px;
  margin-bottom: 5px;
  object-fit: cover;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 70%;
}

.imgFrame > img {
  width: 80px;
}
.recommend_contentFrame:hover .recommend_preview {
  height: 300px;
}

.recommend_contentFrame:hover .tag {
  display: none;
}

.recommend_contentFrame:hover .content_preview {
  top: 20%;
}
</style>
