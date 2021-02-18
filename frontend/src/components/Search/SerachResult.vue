<template>
  <div>
    <div class="resultListFrame">
      <div class="resultHeader">
        <div class="resultImgFrame" @click="goToDetail">
          <img
            v-if="result.indoorResponseDtoList[0].user.img"
            class="resultImgFrame-img"
            :src="
              `https://dtbqjjy7vxgz8.cloudfront.net/${result.indoorResponseDtoList[0].user.img}`
            "
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
          <div 
            v-if="result.indoorResponseDtoList[0].category == 2" 
            @click="goToGroup" 
            class="resultTitle"
          >
            {{ result.indoorResponseDtoList[0].groupName }}
          </div>
          <div class="resultCategory" style="color: #b29887; font-weight: 500">
            <template v-if="result.indoorResponseDtoList[0].category == 1">꽃보다집</template>
            <template v-if="result.indoorResponseDtoList[0].category == 2">핵인싸</template>
            <template v-if="result.indoorResponseDtoList[0].category == 3">청산별곡</template>
            <template v-if="result.indoorResponseDtoList[0].category == 4">워커홀릭</template>
          </div>
          <!-- <div v-if="result.indoorResponseDtoList[0].groupName">{{ result.indoorResponseDtoList[0].groupName }}</div> -->
        </div>
      </div>
      <!-- <div class="resultHeader">sss</div> -->
      <div class="categorytitlehr"></div>
      <div class="resultDetailFrame" @click="goToDetail">
        <div class="resultDetailUper">
          <div class="resultDetail">
            <div class="resulttag">
              <!-- <div class="resultdate">{{ year }}.{{ month }}.{{ day }}</div> -->
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
            <div v-html="result.indoorResponseDtoList[0].content"></div>
          </div>
        </div>
        <div class="resultBottomFrame">
          <div>👏🏻 {{ result.indoorResponseDtoList[0].likeCnt }}</div>
          <div>댓글 {{ result.indoorResponseDtoList[0].commentCnt }}개</div>
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
        query: { name: this.result.indoorResponseDtoList[0].user.id },
      });
    },

    goToDetail() {
      if (this.result.indoorResponseDtoList[0].category == 2) {
        this.$router.push({
          name: "NewsfeedDetail",
          query: { id: this.result.indoorResponseDtoList[0].id, group: this.result.indoorResponseDtoList[0].groupId, Category: 2 },
          params: { fd: this.result.indoorResponseDtoList[0]  },
        });
      } else {
        this.$router.push({
          name: "NewsfeedDetail",
          query: {  id: this.result.indoorResponseDtoList[0].id, Category: this.result.indoorResponseDtoList[0].category },
          params: { fd: this.result.indoorResponseDtoList[0]  },
        });
      }
    },

    goToGroup() {
      this.$router.push({
        name: "groupdetail",
        query: { groupId: this.result.indoorResponseDtoList[0].groupId},
      });
    },



    setDateTime() {
      if (this.result) {
        let date = this.result.indoorResponseDtoList[0].date.split("T")[0];
        this.time = this.result.indoorResponseDtoList[0].date.split("T")[1];
        this.year = date.split("-")[0];
        this.month = date.split("-")[1];
        this.day = date.split("-")[2];
      }
    },
    findTagIdx() {
      if (this.result) {
        let idxz = this.result.indoorResponseDtoList[0].tags.filter(
          (tag, idx) => {
            if (tag === this.result.tagName) {
              return idx;
            }
          }
        );
        this.result.indoorResponseDtoList[0].tags.splice(idxz[0], 1);
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

<style scoped></style>
