<template>
  <splide :options="options">
    <splide-slide v-for="(op, idx) in c" :key="idx">
      <RecommendCard :op="op" :Category="Category"/>
    </splide-slide>
  </splide>
</template>

<script>
import "@/assets/css/splide.css";
import { mapState } from 'vuex'
import { indoorReco } from "@/api/reco.js";
import { groupFeedReco } from "@/api/reco.js";
import { outdoorReco } from "@/api/reco.js";
import RecommendCard from './RecommendCard.vue';

export default {
  name: 'RecoSplide',
  components: { RecommendCard },
  props: {
    Category: Number,
  },
  data() {
    return {
      age: null,
      c: [{}, {}, {}, {}, {}],
      options: {
        rewind: true,
        width: 2000,
        perPage: 1,
        gap: "5px",
        type: "loop",
        padding: {
          right: "12.px",
          left: "12.px",
        },
        pagination: true,
        autoplay: true,
        interval: 5000,
        pauseOnHover: true,
        classes: { pagination: "splide__pagination" },
      },
    };
  },
  created () {
    this.setRecommend();
  },
  mounted () {
    this.ageCalc();
  },
  methods: {
    ageCalc () {
      let start = this.userinfo.split("~")[0]
      this.age = start
    },
    setRecommend () {
      if (this.Category == 1) {
        // 꽃보다 집 추천
        indoorReco(
          (res) => {
            this.c = res.data.feedList
          }
        )
      } else if (this.Category == 2) {
         // 핵인싸
        groupFeedReco(
          (res) => {
            this.c = res.data
          }
        )
        // this.placeName = '대전'
      } else if (this.Category == 3) {
        // 청산별곡
        outdoorReco(
          (res) => {
            this.c = res.data.feedList
          },
          (err) => {
            console.log(err)
          }
        )
      } else if (this.Category == 4) {
        // 워커홀릭
        this.c = [
          {
            tags: [
            '#유튜버',
            '#나도도전',
            '#대세'
            ],
            user: {
              nickname: 'wikiHow',
              img: 'wikiHow_pro.png'
            },
            content: "요즘 유튜버가 그렇게 HOT🔥하다는데? \n 어떻게 시작하는지 A부터 Z까지",
            files: ['워커홀릭_추천1.jpg',],
            link: "https://ko.wikihow.com/%EC%9C%A0%ED%8A%9C%EB%B8%8C-%EC%B1%84%EB%84%90-%EB%A7%8C%EB%93%9C%EB%8A%94-%EB%B2%95"
          },
          {
            tags: [
            '#중장년',
            '#맞춤',
            '#정책',
            ],
            user: {
              nickname: '고용노동부',
              img: '고용노동부_pro.png'
            },
            files: ['워커홀릭_추천2.jpg',],
            content: "나한테 맞는 정책 뭐가있을까? \n 놓칠 수 없는 정책 모음",
            link: "http://www.moel.go.kr/policy/policyinfo/aged/list.do"
          },
        ];
        // if (this.age > 60) {
          this.c.push({
            tags: [
            '#50살',
            '#이제시작',
            '#준비'
            ],
            user: {
              nickname: '50plus',
              img: '50플러스_pro.png'
            },
            content: "50대, 인생 제2막 시작! \n 뭐부터 준비하면 좋을까?",
            files: ['워커홀릭_추천3.png',],
            link: "https://50plus.or.kr/"
          })
        // } else {
          this.c.push({
            tags: [
            '#60살',
            '#일자리',
            '#은퇴반납'
            ],
            user: {
              nickname: '일자리 여기',
              img: '일자리여기_pro.png'
            },
            content: "아직 현역인 우리들!💪 \n 나한테 맞는 일자리 뭐가 있을까?",
            files: ['워커홀릭_추천4.jpg',],
            link: "https://www.seniorro.or.kr:4431/seniorro/main/main.do"
          })
        // }
      }
    },
  },
  computed : {
    ...mapState({
      userinfo : (state) => state.userInfo,
    }),
  }
};
</script>

<style lang="scss" scoped>

</style>