🍳 CookBook App – USER SUPPORT & DOCUMENTATION

  📘 1. INTRODUCTION

    CookBook is a mobile application designed to help users discover, save, and manage recipes easily.
    It provides a simple interface for exploring new meals, viewing detailed recipes, saving favorites, creating shopping lists, and customizing app preferences such as dark mode, language, and units of           measurement.

  🎯 2. PURPOSE OF THE APP

    The main goal of CookBook is to provide a personalized recipe management experience that supports multi-language users and enhances daily cooking routines.
    Users can:

      Browse and view recipes fetched from an online REST API.

      Save recipes to a local “Favorites” list.

      Add items to a personal shopping list.

    Manage app settings including dark mode, notifications, and language preferences.

  🧩 3. KEY FEATURES
  
  Feature	Description
    🔐 User Authentication	Secure login, registration, and logout functionality.
    📖 Recipe Browser	Displays a list of recipes from a public meal API.
    📄 Recipe Details	Full instructions and images for selected recipes.
    ❤️ Favorites	Save and manage favorite recipes locally using SharedPreferences.
    🛒 Shopping List	Add ingredients from recipes and manage them conveniently.
    ⚙️ Settings	Toggle dark mode, enable notifications, choose units, and switch languages.
    🌍 Multi-Language Support	Supports English and two South African languages (e.g., isiZulu and Sesotho).
    🔔 Notifications	Local reminders for new recipes and saved items.
    🧑‍🍳 User-Friendly UI	Simple, modern design with bottom navigation for quick access.
    
🧱 4. APP ARCHITECTURE

    Frontend: Android (Kotlin, XML, Jetpack Components)

      API: RESTful Meal API for recipe data (https://www.themealdb.com/api/json/v1/1/search.php)

      Local Storage: SharedPreferences for favorites, settings, and shopping list

      Language Files: res/values/strings.xml, res/values-zu/strings.xml, res/values-st/strings.xml

      Navigation: Bottom navigation bar with Home, Recipes, Favorites, and Settings fragments

      Build Tools: Android Studio, Gradle

⚙️ 5. INSTALLATION & SETUP

    Clone or download the repository from GitHub:

    git clone https://github.com/<your-username>/CookBookApp.git


      Open the project in Android Studio.

      Wait for Gradle to sync.

      Connect your emulator or Android device.

      Run the app using ▶ Run ‘app’.

👤 6. USING THE APP

    🔑 Login & Registration

      Open the app and sign up with your email and password.

      Once registered, use your credentials to log in.

    🏠 Home Screen

      Displays a welcome message and featured recipe cards.

      Tap a card to view full details.

    🍲 Recipes

      Browse recipes loaded from the public API.

      Tap any recipe to open its detail page.

    💾 Favorites

      When viewing a recipe, tap “Save to Favorites”.

      Visit the Favorites tab to view all saved recipes.

    🛍 Shopping List

      From the Recipe Detail page, tap “Add to Shopping List”.

      Manage your ingredients in the Shopping List screen.

    ⚙️ Settings

      Enable Dark Mode or Notifications.

      Select Measurement Units (e.g., metric/imperial).

      Choose a Language: English, isiZulu, or Sesotho.

      Tap Logout to securely exit the app.

🧮 7. DESIGN CONSIDERATIONS

      Accessibility: Clear fonts, contrast-friendly colors, and simple icons.

      Localization: Language strings stored in multiple XML files.

      Responsiveness: Layouts adapt to all screen sizes.

      Performance: Uses ViewBinding and RecyclerView for smooth scrolling.

      Data Persistence: Favorites and settings stored locally for offline use.

🧪 8. AUTOMATED TESTING & GITHUB ACTIONS

      Automated testing ensures the app runs correctly on all systems.
      A GitHub Actions workflow is set up to:

      Build the project using Gradle.

      Run instrumented and unit tests.

      Check for lint and dependency issues.

      Provide build results in GitHub automatically.

🚀 9. RELEASE NOTES

  📦 Version 1.0 – Prototype

    Login and Registration screens

    Static home and recipes list

    Basic navigation

  🧩 Version 2.0 – Current Release

  ✅ New Additions:

    REST API Integration for live recipes

    Recipe Detail screen with images & instructions

    Favorites management using SharedPreferences

    Shopping List screen

    Local Notification System

    Settings Fragment with Dark Mode and Language Switching

    Multi-language support (English, isiZulu, Sesotho)

    GitHub Actions for automated testing and builds

💡 10. FUTURE ENHANCEMENTS

    Firebase integration for push notifications

    Cloud storage for user favorites and shopping lists

    Advanced recipe filters (diet, cuisine, prep time)

    Offline caching of recipes

    Personalized recipe recommendations

🆘 11. SUPPORT & CONTACT

    If you encounter issues or have feedback:

    📧 Email: monenekwena71@gmail.com

    🐙 GitHub: https://github.com/ST10369269/OPSC6312_POE
