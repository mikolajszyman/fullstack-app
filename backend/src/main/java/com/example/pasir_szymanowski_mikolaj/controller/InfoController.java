package com.example.pasir_szymanowski_mikolaj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/api/info")
    public Info info() {
        return new Info("Aplikacja Budżetowa","1.0","Wiaj w aplikacji budżetowej stworzonej ze Spring Boot!");
    }
    public static class Info {
        private String appName;
        private String version;
        private String message;

        public Info(String appName, String version, String message) {
            this.appName = appName;
            this.version = version;
            this.message = message;
        }

        public String getAppName() {
            return appName;
        }

        public String getVersion() {
            return version;
        }

        public String getMessage() {
            return message;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
