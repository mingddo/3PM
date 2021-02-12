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
          <div>좋아요 {{ result.indoorResponseDtoList[0].likeCnt }}개</div>
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
</style>
