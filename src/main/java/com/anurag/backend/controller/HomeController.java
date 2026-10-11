package com.anurag.backend.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class: HomeController
 * Serves root landing status page, health check endpoint, and API overview.
 */
@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Smart Energy System - Backend API</title>
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
                <style>
                    :root {
                        --bg-base: #0B0F19;
                        --bg-card: rgba(17, 24, 39, 0.7);
                        --border-color: rgba(255, 255, 255, 0.08);
                        --primary: #10B981;
                        --primary-glow: rgba(16, 185, 129, 0.25);
                        --accent-blue: #3B82F6;
                        --text-main: #F3F4F6;
                        --text-muted: #9CA3AF;
                    }
                    * {
                        box-sizing: border-box;
                        margin: 0;
                        padding: 0;
                    }
                    body {
                        font-family: 'Plus Jakarta Sans', sans-serif;
                        background: radial-gradient(circle at 20% 20%, rgba(16, 185, 129, 0.08) 0%, transparent 40%),
                                    radial-gradient(circle at 80% 80%, rgba(59, 130, 246, 0.08) 0%, transparent 40%),
                                    var(--bg-base);
                        color: var(--text-main);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 24px;
                    }
                    .container {
                        max-width: 820px;
                        width: 100%;
                        background: var(--bg-card);
                        backdrop-filter: blur(16px);
                        -webkit-backdrop-filter: blur(16px);
                        border: 1px solid var(--border-color);
                        border-radius: 24px;
                        padding: 40px;
                        box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5), 0 0 40px -10px var(--primary-glow);
                    }
                    .header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        flex-wrap: wrap;
                        gap: 16px;
                        padding-bottom: 28px;
                        border-bottom: 1px solid var(--border-color);
                    }
                    .brand {
                        display: flex;
                        align-items: center;
                        gap: 14px;
                    }
                    .icon {
                        width: 48px;
                        height: 48px;
                        background: linear-gradient(135deg, #10B981 0%, #059669 100%);
                        border-radius: 14px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 24px;
                        box-shadow: 0 8px 16px -4px rgba(16, 185, 129, 0.4);
                    }
                    h1 {
                        font-size: 24px;
                        font-weight: 800;
                        letter-spacing: -0.02em;
                        background: linear-gradient(135deg, #FFFFFF 0%, #E5E7EB 100%);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                    }
                    .subtitle {
                        color: var(--text-muted);
                        font-size: 14px;
                        margin-top: 2px;
                    }
                    .badge {
                        display: inline-flex;
                        align-items: center;
                        gap: 8px;
                        background: rgba(16, 185, 129, 0.12);
                        color: #34D399;
                        border: 1px solid rgba(16, 185, 129, 0.25);
                        padding: 6px 14px;
                        border-radius: 9999px;
                        font-size: 13px;
                        font-weight: 600;
                    }
                    .dot {
                        width: 8px;
                        height: 8px;
                        background: #10B981;
                        border-radius: 50%;
                        box-shadow: 0 0 10px #10B981;
                        animation: pulse 2s infinite;
                    }
                    @keyframes pulse {
                        0%, 100% { opacity: 1; transform: scale(1); }
                        50% { opacity: 0.5; transform: scale(0.85); }
                    }
                    .grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
                        gap: 16px;
                        margin: 28px 0;
                    }
                    .stat-card {
                        background: rgba(255, 255, 255, 0.02);
                        border: 1px solid var(--border-color);
                        padding: 18px 20px;
                        border-radius: 16px;
                    }
                    .stat-label {
                        font-size: 12px;
                        text-transform: uppercase;
                        letter-spacing: 0.05em;
                        color: var(--text-muted);
                        font-weight: 600;
                    }
                    .stat-value {
                        font-size: 16px;
                        font-weight: 700;
                        color: var(--text-main);
                        margin-top: 6px;
                    }
                    .endpoints-title {
                        font-size: 15px;
                        font-weight: 700;
                        margin-bottom: 12px;
                        color: #E5E7EB;
                    }
                    .endpoint-list {
                        display: flex;
                        flex-direction: column;
                        gap: 10px;
                    }
                    .endpoint-item {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        padding: 12px 16px;
                        background: rgba(0, 0, 0, 0.25);
                        border: 1px solid var(--border-color);
                        border-radius: 12px;
                        font-family: 'JetBrains Mono', monospace;
                        font-size: 13px;
                    }
                    .method {
                        font-weight: 700;
                        padding: 4px 8px;
                        border-radius: 6px;
                        font-size: 11px;
                    }
                    .method.get { background: rgba(59, 130, 246, 0.2); color: #60A5FA; }
                    .method.post { background: rgba(16, 185, 129, 0.2); color: #34D399; }
                    .method.del { background: rgba(239, 68, 68, 0.2); color: #F87171; }
                    .path {
                        color: #E5E7EB;
                        text-decoration: none;
                        transition: color 0.2s;
                    }
                    .path:hover {
                        color: #34D399;
                    }
                    .footer {
                        margin-top: 28px;
                        padding-top: 20px;
                        border-top: 1px solid var(--border-color);
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        flex-wrap: wrap;
                        gap: 12px;
                        font-size: 13px;
                        color: var(--text-muted);
                    }
                    .btn {
                        background: #10B981;
                        color: #064E3B;
                        font-weight: 700;
                        text-decoration: none;
                        padding: 8px 18px;
                        border-radius: 10px;
                        transition: all 0.2s ease;
                        font-size: 13px;
                    }
                    .btn:hover {
                        background: #34D399;
                        box-shadow: 0 4px 14px rgba(16, 185, 129, 0.4);
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <div class="brand">
                            <div class="icon">⚡</div>
                            <div>
                                <h1>Smart Energy Backend</h1>
                                <div class="subtitle">Spring Boot 4.x / Java 21 REST API Engine</div>
                            </div>
                        </div>
                        <div class="badge">
                            <span class="dot"></span>
                            API Operational
                        </div>
                    </div>

                    <div class="grid">
                        <div class="stat-card">
                            <div class="stat-label">Hosting Platform</div>
                            <div class="stat-value">Railway Cloud</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-label">Database Engine</div>
                            <div class="stat-value">MySQL Server</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-label">CORS Policy</div>
                            <div class="stat-value">Allowed (*)</div>
                        </div>
                    </div>

                    <div class="endpoints-title">Active API Endpoints</div>
                    <div class="endpoint-list">
                        <div class="endpoint-item">
                            <div style="display:flex; align-items:center; gap:12px;">
                                <span class="method get">GET</span>
                                <a class="path" href="/health" target="_blank">/health</a>
                            </div>
                            <span style="color:var(--text-muted); font-size:12px;">Health & System Ping</span>
                        </div>
                        <div class="endpoint-item">
                            <div style="display:flex; align-items:center; gap:12px;">
                                <span class="method get">GET</span>
                                <a class="path" href="/api/appliances" target="_blank">/api/appliances</a>
                            </div>
                            <span style="color:var(--text-muted); font-size:12px;">Fetch Registered Appliances</span>
                        </div>
                        <div class="endpoint-item">
                            <div style="display:flex; align-items:center; gap:12px;">
                                <span class="method post">POST</span>
                                <span class="path">/api/auth/login</span>
                            </div>
                            <span style="color:var(--text-muted); font-size:12px;">User Authentication</span>
                        </div>
                        <div class="endpoint-item">
                            <div style="display:flex; align-items:center; gap:12px;">
                                <span class="method post">POST</span>
                                <span class="path">/api/auth/register</span>
                            </div>
                            <span style="color:var(--text-muted); font-size:12px;">Create Account</span>
                        </div>
                    </div>

                    <div class="footer">
                        <span>Connected to dedicated cloud MySQL instance.</span>
                        <a href="/health" class="btn" target="_blank">Test /health Endpoint</a>
                    </div>
                </div>
            </body>
            </html>
            """;
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "smart-energy-backend");
        response.put("database", "MySQL");
        response.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }
}
