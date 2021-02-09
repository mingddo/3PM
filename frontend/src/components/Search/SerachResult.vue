<template>
  <div>
    <div class="resultListFrame">
      <div class="resultHeader">
        <div class="resultImgFrame" @click="goToProfile">
          <img
            v-if="result.indoorResponseDtoList[0].user.img"
            class="resultImgFrame-img"
            :src="result.indoorResponseDtoList[0].user.img"
            alt="유저프로필이미지"
          />
          <img
            v-else
            src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
            alt="유저프로필이미지"
            class="resultImgFrame-img"
          />
        </div>
        <div class="resultTitleInfo">
          <div @click="goToProfile" class="resultTitle">
            {{ result.indoorResponseDtoList[0].user.nickname }}
          </div>
          <div class="resultCategory">핵인싸</div>
        </div>
      </div>
      <!-- <div class="resultHeader">sss</div> -->
      <div class="categorytitlehr"></div>
      <div class="resultDetailFrame" @click="gotoDetail">
        <div class="resultDetailUper">
          <div class="resultDetail">
            <div class="resulttag">
              <div class="resultdate">
                {{ result.indoorResponseDtoList[0].date
                }}<i class="fas fa-globe-asia"></i>
              </div>
              <div class="resulttags">
                {{ result.indoorResponseDtoList[0].tags }}
              </div>
            </div>
          </div>
          <div class="resultthumbnail"></div>
        </div>
        <div class="resultBottomFrame">
          <div>{{ result.indoorResponseDtoList[0].likeCnt }}</div>
          <div>댓글 105개</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    category: String,
    result: Object,
  },
  data() {
    return {
      grouplist: [{}, {}, {}, {}],
      search_grouplist: [{}, {}, {}],
    };
  },
  methods: {
    goToProfile() {
      this.$router.push({
        name: "MyPage",
        query: { name: this.result.user.id },
      });
    },
    gotoDetail() {
      this.$router.push({
        name: "NewsfeedDetail",
        query: { id: this.indoorResponseDtoList.id, Category: this.Category },
        params: { fd: this.fd },
      });
    },
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.resultListFrame {
  width: 100%;
  height: 233px;
  margin-bottom: 16px;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}
.resultHeader {
  width: 100%;
  height: 64px;
  padding: 0 16px;
  padding-top: 16px;
  font-size: 18px;
  font-weight: 700;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
}

.resultImgFrame {
  width: 48px;
  height: 48px;
  margin-right: 15px;
  overflow: hidden;
  border-radius: 70%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.resultImgFrame-img {
  width: 80px;
  object-fit: cover;
}
.resultTitleInfo {
  width: 460px;
  height: 48px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
}

.resultTitle {
  width: 460px;
  height: auto;
  font-size: 18px;
  font-weight: 600;
  text-align: left;
  cursor: pointer;
}

.resultCategory {
  width: 460px;
  height: auto;
  text-align: left;
  font-size: 16px;
  font-weight: 400;
}

.categorytitlehr {
  width: 100%;
  height: 1px;
  margin: 16px 0;
  background-color: rgba(17, 17, 17, 0.2);
}
.resultDetailFrame {
  width: 100%;
  height: 136px;
  padding: 0 16px;
  padding-bottom: 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
}

.resultDetailUper {
  width: 100%;
  height: 90px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.resultDetail {
  width: 500px;
  height: 90px;
}
.resulttag {
  width: 100%;
  text-align: left;
  display: flex;
  /* justify-content: flex-start;
  align-items: flex-start; */
}
.resulttags {
  width: 400px;
  word-break: keep-all;
}
.resultdate {
  width: auto;
  margin-right: 7px;
}

.resultthumbnail {
  width: 90px;
  height: 90px;
}

.resultBottomFrame {
  width: 100%;
  height: 20px;

  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
</style>
