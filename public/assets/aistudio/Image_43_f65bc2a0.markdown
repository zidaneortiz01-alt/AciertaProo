---
name: Neo-Brutalist Wayuu EdTech
colors:
  surface: '#fdf9f1'
  surface-dim: '#dddad2'
  surface-bright: '#fdf9f1'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f7f3eb'
  surface-container: '#f1ede6'
  surface-container-high: '#ece8e0'
  surface-container-highest: '#e6e2da'
  on-surface: '#1c1c17'
  on-surface-variant: '#4e4633'
  inverse-surface: '#31302b'
  inverse-on-surface: '#f4f0e8'
  outline: '#7f7661'
  outline-variant: '#d1c5ad'
  surface-tint: '#745b00'
  primary: '#745b00'
  on-primary: '#ffffff'
  primary-container: '#ffd13b'
  on-primary-container: '#725a00'
  inverse-primary: '#eec12a'
  secondary: '#00696e'
  on-secondary: '#ffffff'
  secondary-container: '#61f4fd'
  on-secondary-container: '#006e73'
  tertiary: '#b7006e'
  on-tertiary: '#ffffff'
  tertiary-container: '#ffc7da'
  on-tertiary-container: '#b4006c'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffe08a'
  primary-fixed-dim: '#eec12a'
  on-primary-fixed: '#241a00'
  on-primary-fixed-variant: '#574400'
  secondary-fixed: '#6bf6ff'
  secondary-fixed-dim: '#3edae3'
  on-secondary-fixed: '#002022'
  on-secondary-fixed-variant: '#004f53'
  tertiary-fixed: '#ffd9e4'
  tertiary-fixed-dim: '#ffb0cd'
  on-tertiary-fixed: '#3e0022'
  on-tertiary-fixed-variant: '#8c0053'
  background: '#fdf9f1'
  on-background: '#1c1c17'
  surface-variant: '#e6e2da'
typography:
  headline-xl:
    fontFamily: Space Grotesk
    fontSize: 40px
    fontWeight: '700'
    lineHeight: 48px
    letterSpacing: -0.03em
  headline-xl-mobile:
    fontFamily: Space Grotesk
    fontSize: 30px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Space Grotesk
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Space Grotesk
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 30px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Space Grotesk
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Space Grotesk
    fontSize: 18px
    fontWeight: '700'
    lineHeight: 24px
    letterSpacing: 0em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '500'
    lineHeight: 28px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
  label-lg:
    fontFamily: Space Grotesk
    fontSize: 16px
    fontWeight: '700'
    lineHeight: 20px
    letterSpacing: 0.02em
  label-md:
    fontFamily: Space Grotesk
    fontSize: 14px
    fontWeight: '700'
    lineHeight: 18px
    letterSpacing: 0.03em
  label-sm:
    fontFamily: Space Grotesk
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.04em
  code-snippet:
    fontFamily: JetBrains Mono
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 22px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-desktop: 1.5rem
  margin: 1rem
  margin-desktop: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2.25rem
---

## Brand & Style
This system marries Colombian cultural identity (specifically geometric Wayuu *kanas* textile motifs) with a vibrant, high-utility Neo-Brutalist interface. Built for high-stakes test preparation (Saber Pro) in an offline-first mobile environment, the emotional tone is energetic, tactile, punchy, and utterly dependable.

Key characteristics:
- **Neo-Brutalist Tactility**: Heavy 2px to 2.5px solid dark ink borders (`#1c1c17`), hard-offset drop shadows (2px to 5px without blur), and visible physical press interactions (`translate-x-[2px] translate-y-[2px]`).
- **Warm Sunlight Palette**: Cheerful canary gold combined with energetic teal/cyan and magenta accents against a warm, unbleached canvas (`#fdf9f1`).
- **Micro-Artifacts**: Monospaced engineering status indicators ("OFFLINE READY", seeds, token IDs) juxtaposed against friendly, rounded geometric typography.

## Colors
The palette balances vibrant, punchy highlighter primaries against a grounded, tactile cream surface:

- **Surface Base (`#fdf9f1`)**: An organic, warm parchment neutral rather than cold sterile white, reducing eye fatigue during marathon exam prep.
- **Ink / Contrast (`#1c1c17`)**: Near-black warm ink utilized for borders, hard shadows, labels, and iconography.
- **Primary Yellow (`#ffd13b`)**: Used for active mode states, standout badges, primary call-to-action cards, and cultural geometric trims.
- **Secondary Cyan/Teal (`#00696e` & `#6bf6ff`)**: Symbolizes technical engineering modules, strict timing tools, and connectivity/offline statuses.
- **Tertiary Magenta/Pink (`#b7006e` & `#ffc7da`)**: Serves as a tertiary focal accent for focused single modules, toggles, and contextual alerts.

## Typography
Typography creates clear friction-free hierarchy through three specialized typefaces:
- **Headlines & Labels (Space Grotesk)**: Provides geometric brutalist punch, bold weight, and tight optical tracking. Uppercase usage with slight letter-spacing creates decisive section tags and badges.
- **Body & Paragraphs (Plus Jakarta Sans)**: Highly readable, humanist sans-serif with a 500 medium weight base that prevents harshness when reading complex exam rules.
- **Technical & Seed Identifiers (JetBrains Mono)**: Monospace precision for IDs, seeds, and system status tags (`SIM-2026-B81F`, `seed_84920`).

## Layout & Spacing
The layout follows a responsive fluid single-column structure on mobile devices, constrained by a strict 4px grid interval (values scaled via `0.25rem`, `0.5rem`, `1rem`, `1.5rem`, `2.25rem`).

- **Screen Margins**: `margin: 1rem` (16px) on mobile viewports; expands to `margin-desktop: 2rem` (32px) for larger breakpoints with a centered container (`max-w-2xl` or `max-w-3xl`).
- **Section Stack Rhythm**: Discrete form sections are separated by `space-lg` (24px) to `space-xl` (36px).
- **Component Interiors**: Standard cards use `space-md` (16px) internal padding, maintaining an uncrowded feeling despite heavy outer strokes.

## Elevation & Depth
Depth is strictly neo-brutalist and tactile. The UI eschews diffused ambient blurs in favor of hard-edged, solid offset drop shadows:

- **Surface Base**: Level 0 (no shadow, borders anchor items).
- **Secondary Cards / Buttons**: `shadow-[2.5px_2.5px_0px_#1c1c17]` or `shadow-[3px_3px_0px_#1c1c17]` with `border-2` or `border-[2.5px] border-on-surface`.
- **Primary / Active Cards**: `shadow-[4px_4px_0px_#1c1c17]` on white background containers.
- **Hero CTA Button**: `shadow-[5px_5px_0px_#1c1c17]`.
- **Active Physical Press**: Buttons and clickable radio cards translate diagonally on click (`active:translate-x-[2px] active:translate-y-[2px]`) while collapsing their shadow (`active:shadow-[1px_1px_0px_#1c1c17]`), simulating a mechanical depression.
- **Sticky Overlays**: Frosted top headers and bottom navigation bars use `bg-surface/90 backdrop-blur-xl` combined with a solid dark bottom/top line border.

## Shapes
Shapes employ rounded rectangles defined with soft Neo-Brutalist corners, preventing the brutalist layout from feeling hostile or overly sharp:
- **Cards & Primary Modules**: `rounded-2xl` (16px) for major card enclosures.
- **Badges, Buttons & Icon Tiles**: `rounded-xl` (12px) and `rounded-lg` (8px).
- **Chips, Pills, & Status Indicators**: `rounded-full` (9999px) for pill tags, custom switches, and avatar icons.
- **Stroke Weights**: Uniform `2px` or `2.5px` solid border (`#1c1c17`) across all interactive cards, toggles, badges, and controls.

## Components

### 1. Buttons
- **Hero / Primary CTA**: Large height, `bg-primary-container` (`#ffd13b`), text `#1c1c17`, `font-headline-md font-bold`, framed by `border-[2.5px] border-on-surface` and `shadow-[5px_5px_0px_#1c1c17]`. Contains leading icon in a white bordered tile and trailing action arrow.
- **Icon / Utility Buttons**: Square (`44x44px` or `36x36px`), `rounded-xl`, `bg-surface-container-lowest` or `bg-surface-container-high`, bordered with hard 2px solid offset shadows.
- **Text / Secondary Buttons**: Clean inline-flex buttons with icon, bold Space Grotesk text, subtle color transition to primary without heavy borders.

### 2. Selection Cards (Radio Groups)
- **Inactive State**: `bg-surface-container-low`, `border-[2.5px] border-on-surface`, `shadow-[3px_3px_0px_#1c1c17]`. Radio circle is empty with `bg-surface-container-lowest`.
- **Selected State**: `bg-surface-container-lowest`, `border-[2.5px] border-on-surface`, `shadow-[4px_4px_0px_#1c1c17]`. Radio indicator fills with `bg-primary-container` harboring an inner dark solid dot (`bg-on-surface`).

### 3. Toggles & Switches
- **Pill Track**: `w-16 h-9 rounded-full`, `border-[2.5px] border-on-surface`, `shadow-[2.5px_2.5px_0px_#1c1c17]`.
- **Inactive**: Track `bg-surface-container-highest`, knob translated left (`translate-x-0`), dark knob `bg-inverse-surface` with white text "OFF".
- **Active**: Track `bg-secondary-container` (or `bg-tertiary-container`), knob translated right (`translate-x-7`), light knob `bg-surface-container-lowest` with dark text "ON".

### 4. Chips
- **Module Filter Chips**: `px-3 py-1.5 rounded-xl border-2 border-on-surface font-bold text-[13px] shadow-[2px_2px_0px_#1c1c17]`.
- Unselected: `bg-surface-container-lowest text-on-surface`.
- Selected: `bg-primary-container text-on-surface`.

### 5. Inputs & Technical Fields
- Inputs feature `bg-surface-container-lowest`, `border-2 border-on-surface`, `shadow-[3px_3px_0px_#1c1c17]`, and `font-code-snippet` for monospace data entries. Interactive action buttons (copy, randomize) sit nested inside the right padding track.

### 6. Badges & Section Headers
- Section titles are anchored by chunky pill badges featuring an icon + uppercase Space Grotesk label (`border-[2.5px] border-on-surface shadow-[2.5px_2.5px_0px_#1c1c17]`).