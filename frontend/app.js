const currentPriceEl = document.getElementById('currentPrice');
const countdownEl = document.getElementById('countdown');
const placeBidBtn = document.getElementById('placeBidBtn');
const toastEl = document.getElementById('toast');

let currentPrice = 2150;
let remainingSeconds = 135;
let userBalance = 5000;
let isAuctionLive = true;

function render() {
    currentPriceEl.textContent = currentPrice.toLocaleString();
    countdownEl.textContent = formatTime(remainingSeconds);
}

function formatTime(seconds) {
    const minutes = String(Math.floor(seconds / 60)).padStart(2, '0');
    const secs = String(seconds % 60).padStart(2, '0');
    return `${minutes}:${secs}`;
}

function showToast(message) {
    toastEl.textContent = message;
    toastEl.classList.remove('hidden');
    toastEl.classList.add('visible');
    setTimeout(() => {
        toastEl.classList.remove('visible');
        toastEl.classList.add('hidden');
    }, 2600);
}

placeBidBtn.addEventListener('click', () => {
    if (!isAuctionLive) {
        showToast('Auction has closed.');
        return;
    }

    const nextBid = currentPrice + 100;

    if (userBalance < nextBid) {
        showToast('Insufficient Balance');
        return;
    }

    currentPrice = nextBid;
    render();
    showToast('Bid placed successfully');
});

setInterval(() => {
    if (!isAuctionLive) return;

    remainingSeconds -= 1;
    if (remainingSeconds <= 0) {
        remainingSeconds = 0;
        isAuctionLive = false;
        placeBidBtn.textContent = 'Auction Closed';
        placeBidBtn.disabled = true;
        showToast('Auction Closed');
    }
    render();
}, 1000);

render();
