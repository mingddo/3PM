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
              <div class="resultdate">{{ year }}.{{ month }}.{{ day }}</div>
              <div class="resulttags">
                <button class="tagName">{{ result.tagName }}</button>
                <span v-if="result.indoorResponseDtoList[0].tags">
                  <button
                    v-for="(tag, idx) in result.indoorResponseDtoList[0].tags"
                    :key="idx"
                  >
                    {{ tag }}
                  </button>
                </span>
              </div>
            </div>
            <div>{{ result.indoorResponseDtoList[0].content }}</div>
          </div>
          <div class="resultthumbnail"></div>
        </div>
        <div class="resultBottomFrame">
          <div>👏🏻 {{ result.indoorResponseDtoList[0].likeCnt }}</div>
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
      grouplist: [],
      search_grouplist: [],
      check_img_alternate: false,
      year: 0,
      month: 0,
      day: 0,
      time: null,
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
      console.log();
      this.$router.push({
        name: "NewsfeedDetail",
        query: { id: this.result.indoorResponseDtoList[0].id, Category: "1" },
        params: { fd: this.result.indoorResponseDtoList[0] },
      });
    },
    setDateTime() {
      if (this.result) {
        let date = this.result.indoorResponseDtoList[0].date.split("T")[0];
        this.time = this.result.indoorResponseDtoList[0].date.split("T")[1];
        this.year = date.split("-")[0];
        this.month = date.split("-")[1];
        this.day = date.split("-")[2];
        console.log(this.year, this.month, this.data, this.time);
      }
    },
    findTagIdx() {
      if (this.result) {
        let idxz = this.result.indoorResponseDtoList[0].tags.map((tag, idx) => {
          if (tag === this.result.tagName) {
            return idx;
          }
        });
        this.result.indoorResponseDtoList[0].tags.splice(idxz, 1);
        console.log(this.result.indoorResponseDtoList[0].tags);
      }
    },
  },
  watch: {
    result: function() {
      this.setDateTime();
    },
  },
  mounted() {
    this.setDateTime();
    this.findTagIdx();
  },
};
</script>

<style scoped>
</style>
