<template>
  <splide :options="options">
    <splide-slide v-for="(op, idx) in c" :key="idx">
      <RecommendCard :op="op" :Category="Category"/>
    </splide-slide>
  </splide>
</template>

<script>
import "@/assets/css/splide.css";
import { indoorReco } from '@/api/reco.js'
import { mapState } from 'vuex'
import RecommendCard from './RecommendCard.vue';

export default {
  name: 'RecoSplide',
  components: { RecommendCard },
  props: {
    Category: Number,
    placeName: String,
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
            console.log(res)
            this.c = res.data.feedList
            // for (let i of res.data.feedList) {
            //   let obj = {}
            //   obj.tag = 
            // }
            
            // obj.tag = 
          },
          (err) => {
            console.log(err)
          }
        )
      } else if (this.Category == 2) {
        // 청산별곡
        this.placeName = '대전'
        this.$emit('setPlace', this.placeName)
      } else if (this.Category == 3) {
        // 핵인싸
      } else if (this.Category == 4) {
        // 워커홀릭
        this.c = [
          {
            tag: [
            '#유튜버',
            '#나도도전',
            '#대세'
            ],
            user: {
              nickname: 'wikiHow',
              img: 'https://play-lh.googleusercontent.com/PRyVT9EUZs5elFJfMugM-cRUQM9rzegZiLdheMh-4Oc_ehFmG5lQN6vuFxOx_AN7r50'
            },
            content: "요즘 유튜버가 그렇게 HOT🔥하다는데? \n 어떻게 시작하는지 A부터 Z까지",
            img: 'https://i.ytimg.com/vi/-h6hI4dbJZ8/maxresdefault.jpg',
            link: "https://ko.wikihow.com/%EC%9C%A0%ED%8A%9C%EB%B8%8C-%EC%B1%84%EB%84%90-%EB%A7%8C%EB%93%9C%EB%8A%94-%EB%B2%95"
          },
          {
            tag: [
            '#중장년',
            '#맞춤',
            '#정책',
            ],
            user: {
              nickname: '고용노동부',
              img: 'http://www.moel.go.kr/local/viewer/BBS/2016//2016050311203436f524d8ae404370bd02a8c123cab48b.hwp.files/BIN0001.png'
            },
            img: 'https://i.ytimg.com/vi/TkcVFIRBuAg/maxresdefault.jpg',
            content: "나한테 맞는 정책 뭐가있을까? \n 놓칠 수 없는 정책 모음",
            link: "http://www.moel.go.kr/policy/policyinfo/aged/list.do"
          },
        ];
        if (this.age < 60) {
          this.c.push({
            tag: [
            '#50살',
            '#이제시작',
            '#준비'
            ],
            user: {
              nickname: '50plus',
              img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzfVyF3TTwhO3Ki5GVTXlfbo5QmjghtOdA6w&usqp=CAU'
            },
            content: "50대, 인생 제2막 시작! \n 뭐부터 준비하면 좋을까?",
            img: 'https://blog.kakaocdn.net/dn/deMkqF/btqGIpzCQxg/dDUeVNIUnLg5970ijK9poK/img.png',
            link: "https://50plus.or.kr/"
          })
        } else {
          this.c.push({
            tag: [
            '#60살',
            '#일자리',
            '#은퇴반납'
            ],
            user: {
              nickname: '일자리 여기',
              img: 'https://www.seniorro.or.kr:4431/img/main/m13.png'
            },
            content: "아직 현역인 우리들!💪 \n 나한테 맞는 일자리 뭐가 있을까?",
            img: 'https://i.ytimg.com/vi/Lg9lI0WkgKk/maxresdefault.jpg',
            link: "https://www.seniorro.or.kr:4431/seniorro/main/main.do"
          })
        }
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