# CreativeItemLogger

마인크래프트 서버에서 플레이어가 크리에이티브(Creative) 모드일 때 아이템 관련 활동 (특히 마우스 클릭 이벤트)을 감지하고 로그를 기록하는 간단한 Spigot/Paper 플러그인입니다. 서버 관리자가 크리에이티브 모드 사용 내역을 추적하는 데 도움을 줄 수 있습니다.

## ✨ 주요 기능

* 크리에이티브 모드에서 플레이어의 특정 행동 감지
    * (예: 마우스 좌클릭/우클릭 시) * (예: 아이템 획득 시)
* 감지된 활동 내역을 로그 파일 또는 서버 콘솔에 기록
    * 로그 파일 경로: `plugins/CreativeItemLogger/logs.txt` * (콘솔에도 로깅한다면 명시)
* (만약 있다면) 간단한 설정 옵션 제공 (`config.yml`)

## 💾 설치 방법

1.  **[Releases 페이지](https://github.com/sudapeople/CreativeItemLogger/releases)** 에서 최신 버전의 `.jar` 파일을 다운로드합니다.
2.  다운로드한 `.jar` 파일을 사용 중인 Spigot/Paper 서버의 `plugins` 폴더에 넣습니다.
3.  서버를 재시작하거나, 플러그인 관리 플러그인(예: PlugManX)을 사용하여 로드합니다.

## ⚙️ 설정 (`config.yml`)

플러그인을 처음 실행하면 `plugins/CreativeItemLogger/config.yml` 파일이 생성됩니다. 이 파일을 통해 일부 설정을 변경할 수 있습니다.

```yaml
# 설정 파일 예시입니다. 실제 config.yml 내용을 반영해주세요.
# 로그 파일 활성화 여부
log-to-file: true
# 콘솔 로깅 활성화 여부
log-to-console: false
# 로그 형식
log-format: "[%timestamp%] %player%: %action% with %item%"