# Chess Game Deployment Guide

Your chess game is now ready for free hosting! Here are the best options:

## 🚀 Free Hosting Options

### 1. Railway (Recommended) ⭐
**Why Railway?** Best for Java applications, generous free tier, automatic deployments from GitHub.

**Steps:**
1. Go to [Railway.app](https://railway.app)
2. Sign up with your GitHub account
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your `Chess-Game` repository
5. Railway will automatically detect it's a Java project and deploy!

**Free Tier:** 500 hours/month, $5 credit monthly

### 2. Render
**Steps:**
1. Go to [Render.com](https://render.com)
2. Sign up with GitHub
3. Click "New" → "Web Service"
4. Connect your `Chess-Game` repository
5. Use these settings:
   - **Build Command:** `mvn clean package -DskipTests`
   - **Start Command:** `java -Dserver.port=$PORT -jar target/chess-spring-mvc-1.0.0.jar`

**Free Tier:** 750 hours/month

### 3. Heroku (Limited Free Tier)
**Steps:**
1. Install Heroku CLI
2. Run: `heroku create your-chess-game`
3. Run: `git push heroku main`

**Note:** Heroku removed free tier but offers student credits.

## 🎯 Quick Deploy with Railway (Recommended)

Your repository is already configured with:
- ✅ `Procfile` for process definition
- ✅ `railway.json` for Railway-specific config
- ✅ Spring Boot JAR packaging
- ✅ Port configuration via environment variables

**Just connect your GitHub repo to Railway and it will deploy automatically!**

## 🔧 Configuration Files Added

- **Procfile**: Defines how to start your application
- **railway.json**: Railway-specific deployment configuration
- **application.properties**: Spring Boot configuration with dynamic port
- **Spring Boot structure**: Converted from WAR to JAR for easier deployment

## 🌐 After Deployment

Your chess game will be available at:
- Railway: `https://your-app-name.up.railway.app`
- Render: `https://your-app-name.onrender.com`

## 🎮 Features Available After Deployment

- ✅ Complete chess game with all pieces
- ✅ Real-time multiplayer via WebSockets
- ✅ Beautiful responsive UI
- ✅ Game state persistence
- ✅ Shareable game IDs
- ✅ Move history and validation

## 🐛 Troubleshooting

If deployment fails:
1. Check build logs in your hosting platform
2. Ensure Java 11+ is being used
3. Verify all dependencies are in pom.xml
4. Check that PORT environment variable is set

## 📱 Mobile Support

Your chess game is fully responsive and works on:
- Desktop browsers
- Mobile phones
- Tablets

Ready to deploy! Choose Railway for the easiest experience.
