// Shared utilities for CSRF token management and API calls
class CSRFManager {
    constructor() {
        this.csrfToken = null;
        this.csrfHeaderName = null;
        this.csrfParameterName = null;
    }

    // Fetch CSRF token from backend
    async fetchCSRFToken() {
        try {
            const response = await fetch('http://localhost:8080/CCM/csrfToken', {
                method: 'GET',
                credentials: 'include'
            });
            
            if (response.ok) {
                const tokenData = await response.json();
                this.csrfToken = tokenData.token;
                this.csrfHeaderName = tokenData.headerName;
                this.csrfParameterName = tokenData.parameterName;
                console.log('CSRF token fetched successfully');
                return true;
            } else {
                console.error('Failed to fetch CSRF token');
                return false;
            }
        } catch (error) {
            console.error('Error fetching CSRF token:', error);
            return false;
        }
    }

    // Get headers with CSRF token
    getHeaders() {
        const headers = {
            'Content-Type': 'application/json'
        };
        
        if (this.csrfToken && this.csrfHeaderName) {
            headers[this.csrfHeaderName] = this.csrfToken;
        }
        
        return headers;
    }

    // Make authenticated API call
    async makeAuthenticatedRequest(url, options = {}) {
        // Ensure we have CSRF token
        if (!this.csrfToken) {
            const tokenFetched = await this.fetchCSRFToken();
            if (!tokenFetched) {
                throw new Error('Failed to obtain CSRF token');
            }
        }

        const defaultOptions = {
            credentials: 'include',
            headers: this.getHeaders()
        };

        const finalOptions = { ...defaultOptions, ...options };
        
        try {
            const response = await fetch(url, finalOptions);
            return response;
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }

    // Clear CSRF token (for logout)
    clearToken() {
        this.csrfToken = null;
        this.csrfHeaderName = null;
        this.csrfParameterName = null;
    }
}

// Global CSRF manager instance
const csrfManager = new CSRFManager();

// Utility functions
function showMessage(message, type = 'info') {
    const messageDiv = document.getElementById('message');
    if (messageDiv) {
        messageDiv.textContent = message;
        messageDiv.className = `message ${type}`;
        messageDiv.style.display = 'block';
        
        // Auto-hide after 5 seconds
        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 5000);
    }
}

function hideMessage() {
    const messageDiv = document.getElementById('message');
    if (messageDiv) {
        messageDiv.style.display = 'none';
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
}

function formatTime(timeString) {
    const time = new Date('1970-01-01T' + timeString);
    return time.toLocaleTimeString();
}

// Initialize CSRF token when page loads
document.addEventListener('DOMContentLoaded', async () => {
    await csrfManager.fetchCSRFToken();
});