# 팀명
- 팀명 :
- 팀원 소개
  팀장 : 김상원
  팀원 : 명도균, 박성호, 이병훈, 장수민, 차민석

## Gantt

```mermaid
gantt
    dateFormat  YYYY-MM-DD
    title       Adding GANTT diagram functionality to mermaid
    %% excludes    weekends
    %% (`excludes` accepts specific dates in YYYY-MM-DD format, days of the week ("sunday") or "weekends", but not the word "weekdays".)

    section 프로젝트 준비
    주제 선정                  :done,    2021-01-18, 5d
    기능 목록 상세 도출         :active,  2021-01-19, 3d
    화면 기획                  :done,    2021-01-20, 4d
    개발 환경 구성             :done,    2021-01-15, 3d

    section Critical tasks
    Completed task in the critical line :crit, done, 2021-01-06,24h
    Implement parser and jison          :crit, done, after des1, 2d
    Create tests for parser             :crit, active, 3d
    Future task in critical line        :crit, 5d
    Create tests for renderer           :2d
    Add to mermaid                      :1d

    section Documentation
    Describe gantt syntax               :active, a1, after des1, 3d
    Add gantt diagram to demo page      :after a1  , 20h
    Add another diagram to demo page    :doc1, after a1  , 48h

    section Last section
    Describe gantt syntax               :after doc1, 3d
    Add gantt diagram to demo page      :20h
    Add another diagram to demo page    :48h

## 주제


## 기술스택


## 기능


## 개발규칙