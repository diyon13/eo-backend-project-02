/**
 * 쪽지함 공통 기능
 * - 모든 쪽지 페이지(목록, 읽기, 쓰기)에서 배지 카운트 관리
 */
const MessageCommon = {
    // 배지 업데이트 API 호출
    updateUnreadBadge() {
        const badge = document.querySelector('#unreadBadge');
        if (!badge) return;

        fetch('/messages/api/unread-count')
            .then(res => res.ok ? res.json() : Promise.reject())
            .then(count => {
                if (count > 0) {
                    badge.textContent = count > 99 ? '99+' : count;
                    badge.style.display = 'inline-block';
                } else {
                    badge.style.display = 'none';
                }
            })
            .catch(err => console.error('공통 배지 업데이트 실패:', err));
    },

    // 초기화: 페이지 로드 시 실행
    init() {
        this.updateUnreadBadge();
    }
};

// DOM 로드 시 공통 초기화 실행
document.addEventListener('DOMContentLoaded', () => MessageCommon.init());