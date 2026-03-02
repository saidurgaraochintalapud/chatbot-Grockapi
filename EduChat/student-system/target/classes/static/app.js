document.addEventListener('DOMContentLoaded', () => {
    const chatForm = document.getElementById('chatForm');
    const userInput = document.getElementById('userInput');
    const chatHistory = document.getElementById('chatHistory');
    const clearBtn = document.getElementById('clearBtn');

    // Load history
    fetchHistory();

    // Clear history
    clearBtn.addEventListener('click', () => {
        if (!confirm('Are you sure you want to clear the chat history?')) return;

        fetch('/api/clear', { method: 'POST' })
            .then(() => {
                chatHistory.innerHTML = ''; // Clear UI
            })
            .catch(err => console.error("Could not clear history", err));
    });

    chatForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const query = userInput.value.trim();
        if (!query) return;

        // 1. Add User Message to UI
        appendMessage(query, 'user');
        userInput.value = '';

        // 2. Send to Backend
        fetch('/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ query: query })
        })
            .then(res => res.json())
            .then(data => {
                // 3. Add Bot Response to UI
                appendMessage(data.content, 'bot');
            })
            .catch(err => {
                console.error(err);
                appendMessage("Sorry, I'm having trouble connecting.", 'bot');
            });
    });

    function appendMessage(text, sender) {
        // Wrapper
        const wrapperDiv = document.createElement('div');
        wrapperDiv.className = `message-wrapper ${sender === 'user' ? 'user-wrapper' : 'bot-wrapper'}`;

        // Avatar
        const avatarDiv = document.createElement('div');
        avatarDiv.className = `avatar ${sender === 'user' ? 'user-avatar' : 'bot-avatar'}`;

        // High-quality Inline SVGs
        if (sender === 'user') {
            // Shiny User Icon
            avatarDiv.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>`;
        } else {
            // Brain/Sparkle AI Icon
            avatarDiv.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 16a4 4 0 1 0 0-8 4 4 0 0 0 0 8z"/><path d="M12 3v1"/><path d="M12 20v1"/><path d="M3 12h1"/><path d="M20 12h1"/><path d="m18.364 5.636-.707.707"/><path d="m6.343 17.657-.707.707"/><path d="m5.636 5.636.707.707"/><path d="m17.657 17.657.707.707"/></svg>`;
        }

        // Content
        const contentDiv = document.createElement('div');
        contentDiv.className = 'message-content';

        // Render Markdown for bot, plain text for user (for safety/styling)
        if (sender === 'user') {
            contentDiv.textContent = text;
        } else {
            contentDiv.innerHTML = marked.parse(text);
        }

        wrapperDiv.appendChild(avatarDiv);
        wrapperDiv.appendChild(contentDiv);

        chatHistory.appendChild(wrapperDiv);

        // Scroll to bottom
        scrollToBottom();
    }

    function fetchHistory() {
        fetch('/api/history')
            .then(res => res.json())
            .then(data => {
                chatHistory.innerHTML = ''; // Clear initial clean slate if any
                data.forEach(msg => {
                    const sender = msg.role === 'user' ? 'user' : 'bot'; // 'bot' maps to 'assistant' logic in backend but reusing 'bot' class handling
                    // Fix: Backend stores "assistant", we need to map that.
                    // Actually, let's fix the mapping right here:
                    const uiSender = (msg.role === 'user') ? 'user' : 'bot';
                    appendMessage(msg.content, uiSender);
                });
                scrollToBottom();
            })
            .catch(err => console.error("Could not load history", err));
    }

    function scrollToBottom() {
        chatHistory.scrollTop = chatHistory.scrollHeight;
    }
});
