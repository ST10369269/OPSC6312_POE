CookBook Android Application Report
1. Project Overview
1.1 Purpose of the Application
The Cookbook App is a mobile recipe management system designed to help users conveniently explore, save, and organize meal recipes. It allows users to:
•	Browse and view detailed recipes fetched from a public REST API (TheMealDB).
•	Save their favorite recipes for offline access.
•	Create a personalized shopping list based on ingredients.
•	Manage their user profile and settings.
•	Benefit from innovative features such as Mood-Based Filtering, Interactive Cook Mode, and Smart Shopping List.
The application aims to enhance user convenience in meal planning and cooking by integrating interactive, dynamic, and intelligent features.

2. Design Considerations
2.1 User Interface (UI)
The UI is designed using Android Studio with Kotlin and follows Material Design principles:
•	Clean, intuitive layout with consistent colors and typography.
•	Bottom Navigation Bar for seamless access to major sections: Home, Recipes, Shopping List, and Settings.
•	Activity and Fragment-based architecture for modular design and easier navigation.
2.2 User Experience (UX)
Key UX goals include:
•	Simplicity: Easy-to-navigate screens for all age groups.
•	Responsiveness: Optimized for different screen sizes and device orientations.
•	Engagement: Use of visuals, icons, and animations to make interaction enjoyable.
2.3 Technical Design
•	Architecture: MVVM (Model-View-ViewModel) for separation of concerns.
•	Data Layer: Uses Retrofit for API calls and Room Database for local storage.
•	Authentication: Firebase Authentication for user sign-up and login.
•	Offline Access: Caching recipes for offline viewing using Room Database.
•	API Integration: Fetching recipes dynamically from TheMealDB REST API.

2.4 Accessibility
•	Text contrast, font size, and button spacing follow WCAG guidelines.
•	Voice instructions enable inclusive cooking experiences for visually impaired users.

3. GitHub & GitHub Actions Utilisation
3.1 GitHub Repository Management
The CookBook App project is hosted on GitHub, providing:
•	Version control for all source files.
•	Collaboration features like branches and pull requests.
•	Centralized storage for code, documentation, and assets.
Branching strategy:
•	main.
•	master (Active development branch)

3.2 GitHub Actions Workflow
GitHub Actions automate the build and test processes whenever code is pushed or a pull request is opened.
Example workflow file: .github/workflows/android-ci.yml
This ensures:
•	Every new commit is automatically built and tested.
•	Errors are caught early before merging.
•	The app remains consistent across environments — not just on the developer’s computer.

4. Automated Testing
4.1 Testing Frameworks
•	JUnit for unit testing logic and API responses.
•	Espresso for UI interaction tests.
•	MockWebServer for API response testing without actual network calls.
4.2 Test Objectives
•	Verify recipe fetching from the API.
•	Confirm Firebase authentication functions (login, signup, logout).
•	Validate navigation between screens.
•	Ensure offline recipe caching functions as expected.
4.3 Continuous Testing via GitHub Actions
Whenever code is pushed:
•	Tests run automatically.
•	If a test fails, the developer is notified via GitHub Actions logs.
•	Successful builds indicate the app is stable for release.

5. Release Notes
Version 1.0.0 (Prototype)
Release Date: June 2025
Highlights:
•	Basic splash screen, login, and register activities.
•	Bottom navigation structure.
•	Placeholder fragments for Home, Recipes, and Settings.
•	Static recipe list with sample data.

Version 1.1.0 (Current Release)
Release Date: October 2025
Enhancements and Updates:
•	Integrated TheMealDB REST API for real recipe fetching.
•	Added RecipeDetailActivity with image, ingredients, and instructions.
•	Created Shopping List Screen with dynamic item addition and deletion.
•	Implemented Firebase Authentication (login/register/logout).
•	Improved UI design with Material 3 components.

Innovative Features Added
a) Interactive Cook Mode (Hands-Free Guidance)
Inspired by: Tasty’s step-by-step video tutorials
My twist:
Introduce a “Cook Mode” - a voice-guided, hands-free recipe experience.
Features:
•	Each recipe step displayed in large, swipeable cards
•	Voice commands like “Next step”, “Repeat”, “Set 5-minute timer”
•	Background music toggle while cooking
Value Added:
•	Fun and modern
•	Improves accessibility (no touching screen with messy hands)
•	Differentiates you from static text-based recipe apps

b) Mood-Based Meal Suggestions (Emotional AI)
Inspired by: Yummly’s personalization + Paprika’s meal planning
My twist:
Let users choose their current mood or energy level, and recommend meals that match it.
Example:
•	Feeling lazy → “5-Minute Recipes”
•	Feeling creative → “Chef Mode: Try something new”
•	Feeling down → “Comfort Food”
•	Gym day → “High-Protein Picks”
Value Added:
•	Emotional connection with users
•	Combines nutrition + emotion
•	Easy to gamify with streaks or badges

c) Smart Shopping List (Offline and Sync Later)
Complexity: easy
Why it’s simple:
•	Built with RoomDB or SQLite for offline storage.
•	When the user comes online, the data syncs to Firebase.
Implementation idea:
•	Each recipe can have an “Add Ingredients to List” button.
•	The app stores items locally and marks them as “purchased” or “pending.”
•	Syncs automatically when the internet returns.
Why it’s worth it:
•	Combines offline and cloud features (showing real skill).
•	Very practical — users always need this feature.


6. Conclusion
The CookBook Android App integrates modern mobile technologies and automation practices to deliver a high-quality, maintainable, and scalable solution.
Through the combination of Firebase, REST APIs, Room Database, and GitHub Actions, the project ensures:
•	Reliability (automated testing/builds)
•	Maintainability (modular architecture)
•	User satisfaction (intuitive UI + innovative features)
This report documents the full lifecycle and development strategy, reflecting a professional software engineering workflow.

